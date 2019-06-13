package com.rappi.test.rappitestapp.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rappi.test.rappitestapp.R;
import com.rappi.test.rappitestapp.model.beans.TMDBMovie;
import com.rappi.test.rappitestapp.viewmodel.MoviesViewModel;

import java.util.LinkedList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter {

    class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView noMoviesTextView;
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView thumbnailImageView;
        View root;

        public MovieViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.movie_title_tv);
            descriptionTextView = view.findViewById(R.id.movie_description_tv);
            thumbnailImageView = view.findViewById(R.id.movie_thumbnail_iv);
            root = view.findViewById(R.id.root);
        }

    }

    private Context context;
    private List<TMDBMovie> movies;
    private String searchQueryFilter;
    private List<TMDBMovie> filteredMovies;
    private MoviesRecyclerRowClickListener listener;

    public MoviesAdapter(Context context, @NonNull  List<TMDBMovie> movies, String searchQuery, MoviesRecyclerRowClickListener eventListener) {
        this.context = context;
        this.movies = movies;
        this.searchQueryFilter = searchQuery;
        this.listener = eventListener;

        doFilter();
    }

    /**
     * It applies filter to movies list based on searchQueryFilter
     */
    private void doFilter() {
        if (searchQueryFilter == null || "".equals(searchQueryFilter) || movies.size() == 0) {
            filteredMovies = movies;
            return;
        }

        filteredMovies = new LinkedList<>();
        for (TMDBMovie currentMovie : movies) {
            if (currentMovie.getTitle().toLowerCase().contains(searchQueryFilter.toLowerCase())) {
                filteredMovies.add(currentMovie);
            }
        }
    }

    public void setSearchQueryFilter(String searchQueryFilter) {
        this.searchQueryFilter = searchQueryFilter;

        doFilter();
    }

    public void updateMovies(List<TMDBMovie> updatedMovies) {
        movies = updatedMovies;

        doFilter();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_movie_row, viewGroup, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final TMDBMovie movie = filteredMovies.get(i);

        MovieViewHolder movieHolder = (MovieViewHolder) viewHolder;
        movieHolder.titleTextView.setText(movie.getTitle());
        movieHolder.descriptionTextView.setText(movie.getOverview());
        movieHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMovieRowClicked(movie.getId());
            }
        });

        if (isImageReady()) {
            Glide.with(context)
                    .load(buildImageUrl(movie))
                    .into(movieHolder.thumbnailImageView);
        }
    }

    @Override
    public int getItemCount() {
        return filteredMovies.size();
    }

    private boolean isImageReady() {
        return MoviesViewModel.RETROFIT_IMAGE_BASE_URL != null;
    }

    private String buildImageUrl(TMDBMovie movie) {
        Uri.Builder builder = Uri.parse(MoviesViewModel.RETROFIT_IMAGE_BASE_URL)
                                    .buildUpon().appendPath("w342")
                                    .appendPath(movie.getPosterPath().replace("/", ""));

        return builder.build().toString();
    }

}
