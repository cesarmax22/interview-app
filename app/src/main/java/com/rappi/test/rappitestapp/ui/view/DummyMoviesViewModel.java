package com.rappi.test.rappitestapp.ui.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.rappi.test.rappitestapp.beans.MovieCategory;
import com.rappi.test.rappitestapp.beans.TMDBMovie;
import com.rappi.test.rappitestapp.beans.TMDBMoviesResponse;
import com.rappi.test.rappitestapp.ws.retrofit.API;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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