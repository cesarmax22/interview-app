package com.rappi.test.rappitestapp.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.rappi.test.rappitestapp.R;
import com.rappi.test.rappitestapp.beans.MovieCategory;
import com.rappi.test.rappitestapp.beans.TMDBImagesConfigurations;
import com.rappi.test.rappitestapp.beans.TMDBMovie;
import com.rappi.test.rappitestapp.beans.TMDBMoviesResponse;
import com.rappi.test.rappitestapp.ui.adapters.MoviesAdapter;
import com.rappi.test.rappitestapp.ui.adapters.MoviesRecyclerRowClickListener;
import com.rappi.test.rappitestapp.ui.view.DummyMoviesViewModel;
import com.rappi.test.rappitestapp.ui.view.MoviesViewModel;
import com.rappi.test.rappitestapp.ui.view.SearchViewModel;

import java.util.LinkedList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements MoviesRecyclerRowClickListener {

    private static final String ARG_MOVIE_CATEGORY = "movie-category-key";

    // TODO: creo q no voy a usar observer-observable
    private MoviesViewModel moviesViewModel;
    private SearchViewModel searchViewModel;

    private RecyclerView moviesRecyclerView;
    private MoviesAdapter moviesAdapter;

    public static PlaceholderFragment newInstance(MovieCategory category) {
        PlaceholderFragment fragment = new PlaceholderFragment();
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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        /*if (moviesAdapter == null)
            // only instantiate if activity is first time being created
            moviesAdapter = new MoviesAdapter(getContext(), new LinkedList<TMDBMovie>());*/
        searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        if (savedInstanceState == null)
            // only instantiate if activity is first time being created
            moviesAdapter = new MoviesAdapter(getContext(), new LinkedList<TMDBMovie>(), searchViewModel.getSearchQuery(), this);

        //searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
        searchViewModel.getSearch().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                moviesAdapter.setSearchQueryFilter(s);
                moviesAdapter.notifyDataSetChanged();
            }
        });

        //moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.getMovies((MovieCategory) getArguments().getSerializable(ARG_MOVIE_CATEGORY)).observe(this, new Observer<TMDBMoviesResponse>() {
            @Override
            public void onChanged(@Nullable TMDBMoviesResponse tmdbMovies) {
                // TODO: check if it's better to instantiate a new adapter or set the new list and update it
                moviesAdapter = new MoviesAdapter(getContext(), tmdbMovies.getResults(), searchViewModel.getSearchQuery(), PlaceholderFragment.this);
                moviesRecyclerView.setAdapter(moviesAdapter);
                //moviesAdapter.updateMovies(tmdbMovies.getResults());

                //moviesAdapter.notifyDataSetChanged();
            }
        });

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
    public void onMovieRowClicked(final int movieId) {
        moviesViewModel.getMovieDetails(movieId).observe(this, new Observer<TMDBMovie>() {
            @Override
            public void onChanged(@Nullable TMDBMovie tmdbMovie) {
                // TODO: open dialog!
                Toast.makeText(getContext(), "OK response de movie details!" + movieId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}