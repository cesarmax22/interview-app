package com.rappi.test.rappitestapp.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rappi.test.rappitestapp.R;
import com.rappi.test.rappitestapp.model.beans.MovieCategory;
import com.rappi.test.rappitestapp.model.beans.TMDBImagesConfigurations;
import com.rappi.test.rappitestapp.model.beans.TMDBMovie;
import com.rappi.test.rappitestapp.model.beans.TMDBMoviesResponse;
import com.rappi.test.rappitestapp.ui.adapters.MoviesAdapter;
import com.rappi.test.rappitestapp.ui.adapters.MoviesRecyclerRowClickListener;
import com.rappi.test.rappitestapp.viewmodel.MoviesViewModel;
import com.rappi.test.rappitestapp.viewmodel.SearchViewModel;

import java.util.LinkedList;

/**
 * A placeholder fragment containing information about list of movies. It is reused for all available tabs.
 */
public class MoviePlaceholderFragment extends Fragment implements MoviesRecyclerRowClickListener {

    private static final String ARG_MOVIE_CATEGORY = "movie-category-key";

    private MoviesViewModel moviesViewModel;
    private SearchViewModel searchViewModel;

    private RecyclerView moviesRecyclerView;
    private MoviesAdapter moviesAdapter;

    private AlertDialog loadingDialog;

    public static MoviePlaceholderFragment newInstance(@NonNull MovieCategory category) {
        MoviePlaceholderFragment fragment = new MoviePlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MOVIE_CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        moviesRecyclerView = root.findViewById(R.id.recycler_view_movies);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        buildLoadingDialog();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        loadingDialog.show();

        searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        moviesAdapter = new MoviesAdapter(getContext(), new LinkedList<TMDBMovie>(), searchViewModel.getSearchQuery(), this);

        // start observing for search model updates. when getting an event, we know that we have to filter movies based on given query
        searchViewModel.getSearch().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                moviesAdapter.setSearchQueryFilter(s);
                moviesAdapter.notifyDataSetChanged();
            }
        });

        // start observing for list of movies for this particular category. when getting an event, we know that we have to display movies
        moviesViewModel.getMovies((MovieCategory) getArguments().getSerializable(ARG_MOVIE_CATEGORY)).observe(this, new Observer<TMDBMoviesResponse>() {
            @Override
            public void onChanged(@Nullable TMDBMoviesResponse tmdbMovies) {
                loadingDialog.dismiss();

                moviesAdapter = new MoviesAdapter(getContext(), tmdbMovies.getResults(), searchViewModel.getSearchQuery(), MoviePlaceholderFragment.this);
                moviesRecyclerView.setAdapter(moviesAdapter);
            }
        });

        // start observing for image configuration. this is needed so to be able to retrieve movies thumbnails. "themoviedb" API specific logic
        moviesViewModel.getImageConfigurations().observe(this, new Observer<TMDBImagesConfigurations>() {
            @Override
            public void onChanged(@Nullable TMDBImagesConfigurations tmdbImagesConfigurations) {
                // refresh adapter so to draw images
                moviesAdapter.notifyDataSetChanged();
            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        loadingDialog.dismiss();

        super.onPause();
    }

    @Override
    public void onMovieRowClicked(final int movieId) {
        loadingDialog.show();

        // start observing for movie details. when response is back, we 'll display data as a dialog
        moviesViewModel.getMovieDetails(movieId).observe(this, new Observer<TMDBMovie>() {
            @Override
            public void onChanged(@Nullable TMDBMovie tmdbMovie) {
                loadingDialog.dismiss();

                if (tmdbMovie != null && tmdbMovie.getVideos() != null && tmdbMovie.getVideos().getResults() != null && tmdbMovie.getVideos().getResults().length > 0) {
                    MovieDetailsDialogFragment.newInstance(tmdbMovie)
                            .show(getFragmentManager(), "movie-detail-dialog");
                } else {
                    Toast.makeText(getContext(), "Movie details not available!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void buildLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.loading_dialog);
        loadingDialog = builder.create();
    }
}