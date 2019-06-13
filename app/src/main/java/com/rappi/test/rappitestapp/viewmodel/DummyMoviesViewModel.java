package com.rappi.test.rappitestapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.rappi.test.rappitestapp.model.beans.MovieCategory;
import com.rappi.test.rappitestapp.model.beans.TMDBMovie;
import com.rappi.test.rappitestapp.model.beans.TMDBMoviesResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * Intended to be used as a dummy provider while coding or testing.
 */
public class DummyMoviesViewModel extends MoviesViewModel {

    @Override
    public LiveData<TMDBMoviesResponse> getMovies(@NonNull MovieCategory category) {

        TMDBMoviesResponse dummyResponse = new TMDBMoviesResponse();
        List<TMDBMovie> dummyList = new LinkedList<>();
        dummyResponse.setResults(dummyList);

        switch (category) {
            case POPULAR:
                dummyList.add(createDummyMovie("POPULAR"));
                break;
            case TOP_RATED:
                dummyList.add(createDummyMovie("TOP RATED"));
                break;
            case UPCOMING:
                dummyList.add(createDummyMovie("UPCOMING"));
                break;
        }
        dummyList.add(createDummyMovie("Godfather"));
        dummyList.add(createDummyMovie("Godfather 4"));
        dummyList.add(createDummyMovie("Thundercats"));
        dummyList.add(createDummyMovie("Atreyu"));
        dummyList.add(createDummyMovie("Rambo"));
        dummyList.add(createDummyMovie("Rambo II"));
        dummyList.add(createDummyMovie("Aladin"));
        dummyList.add(createDummyMovie("Dumbo"));
        dummyList.add(createDummyMovie("Love actually"));
        dummyList.add(createDummyMovie("Another movie"));
        dummyList.add(createDummyMovie("no idea!"));
        dummyList.add(createDummyMovie("a tv show"));

        moviesList = new MutableLiveData<>();
        ((MutableLiveData<TMDBMoviesResponse>) moviesList).setValue(dummyResponse);
        return moviesList;
    }

    public DummyMoviesViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void refreshMovies() {
        moviesList = new MutableLiveData<TMDBMoviesResponse>();
        TMDBMoviesResponse dummyResponse = new TMDBMoviesResponse();
        List<TMDBMovie> dummyList = new LinkedList<>();
        dummyResponse.setResults(dummyList);
        switch (movieCategory) {
            case POPULAR:
                dummyList.add(createDummyMovie("POPULAR REFRESHED"));
                break;
            case TOP_RATED:
                dummyList.add(createDummyMovie("TOP RATED REFRESHED"));
                break;
            case UPCOMING:
                dummyList.add(createDummyMovie("UPCOMING REFRESHED"));
                break;
        }

        moviesList.postValue(dummyResponse);
    }

    private TMDBMovie createDummyMovie(String title) {
        TMDBMovie dummyMovie = new TMDBMovie();
        dummyMovie.setTitle(title);

        return dummyMovie;
    }
}