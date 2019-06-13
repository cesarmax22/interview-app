package com.rappi.test.rappitestapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.rappi.test.rappitestapp.R;
import com.rappi.test.rappitestapp.model.beans.TMDBMovie;

/**
 * This fragment class is used to display information about a particular movie.
 * I decided to display it as a dialog, it was just a personal decision.
 * It can be refactored to be displayed differently.
 *
 * I decided to use Youtube API to display video trailer of the movie.
 */
public class MovieDetailsDialogFragment extends DialogFragment {

    private static final String ARGS_MOVIE_KEY = "movie-key-arg";

    // youtube api developer key. in a real app this key should be stored in a more secure way!
    private static final String YOUTUBE_API_KEY = "AIzaSyClaQgPtxm9eGsPtLrRzZ8vELMtuYZ23iw";

    // specific movie to display details of.
    private TMDBMovie movie;

    public static MovieDetailsDialogFragment newInstance(TMDBMovie movie) {
        MovieDetailsDialogFragment dialog = new MovieDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_MOVIE_KEY, movie);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        movie = getArguments().getParcelable(ARGS_MOVIE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_details_dialog, container);

        ((TextView) view.findViewById(R.id.title_tv)).setText(movie.getTitle());
        if (movie.isAdult()) {
            ((TextView) view.findViewById(R.id.adult_content_tv)).setText(getResources().getString(R.string.adult_content));
        } else {
            ((TextView) view.findViewById(R.id.adult_content_tv)).setText(getResources().getString(R.string.no_adult_content));
        }
        ((TextView) view.findViewById(R.id.vote_average_tv)).setText(getResources().getString(R.string.vote_average, movie.getVoteAverage()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        YouTubePlayerSupportFragment youtubeFragment = YouTubePlayerSupportFragment.newInstance();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout,  youtubeFragment,"youtube-fragment")
                .commit();

        youtubeFragment.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // for this app purposes, let's just get 1st available video!
                String videoKey = movie.getVideos().getResults()[0].getKey();
                youTubePlayer.loadVideo(videoKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getContext(), R.string.youtube_video_failed, Toast.LENGTH_LONG).show();
            }
        });

    }
}
