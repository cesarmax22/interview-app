package com.rappi.test.rappitestapp.ui.view;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.rappi.test.rappitestapp.beans.MovieCategory;
import com.rappi.test.rappitestapp.beans.TMDBImagesConfigurations;
import com.rappi.test.rappitestapp.beans.TMDBMovie;
import com.rappi.test.rappitestapp.beans.TMDBMoviesResponse;
import com.rappi.test.rappitestapp.ws.retrofit.API;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesViewModel extends ViewModel {

    // we will fetch this data async
    protected MutableLiveData<TMDBMoviesResponse> moviesList;

    private Map<Integer, MutableLiveData<TMDBMovie>> moviesDetailsMap = new HashMap<>();

    protected MovieCategory movieCategory;

    /**
     * Image base url to retrieve images through retrofit. This is fetched from server one time only.
     */
    public static String RETROFIT_IMAGE_BASE_URL = null;

    // TODO: should this be mutable?
    private MutableLiveData<TMDBImagesConfigurations> imagesConfigurations;

    /**
     * Get movies remotely.
     *
     * @param category mandatory for this test app purposes
     * @return
     */
    public LiveData<TMDBMoviesResponse> getMovies(@NonNull MovieCategory category) {

        movieCategory = category;

        // do not load movies if already loaded
        if (moviesList == null) {
            // TODO: not sure if we should always initialize it! check it please
            moviesList = new MutableLiveData<>();

            loadMovies(category);
        }

        return moviesList;
    }

    /**
     * Get configuration so to be able to retrieve movie images later.
     * @return
     */
    public LiveData<TMDBImagesConfigurations> getImageConfigurations() {
        if (imagesConfigurations == null) {
            imagesConfigurations = new MutableLiveData<>();

            loadImagesConfigurations();
        }

        return imagesConfigurations;
    }

    public LiveData<TMDBMovie> getMovieDetails(int movieId) {
        if (!moviesDetailsMap.containsKey(movieId)) {
            moviesDetailsMap.put(movieId, new MutableLiveData<TMDBMovie>());

            loadMovieDetails(movieId);
        }

        return moviesDetailsMap.get(movieId);
    }

    /**
     * It refreshes data from server and notifies accordingly based on model view pattern.
     */
    public void refreshMovies() {
        loadMovies(movieCategory);
    }

    private void loadMovieDetails(final int movieId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<TMDBMovie> call = api.getMovieDetail(movieId, API.API_KEY);
        call.enqueue(new Callback<TMDBMovie>() {
            @Override
            public void onResponse(Call<TMDBMovie> call, Response<TMDBMovie> response) {
                moviesDetailsMap.get(movieId).postValue(response.body());
            }

            @Override
            public void onFailure(Call<TMDBMovie> call, Throwable t) {
                // TODO: what to do here?
            }
        });
    }

    private void loadImagesConfigurations() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<TMDBImagesConfigurations> call = api.getImagesConfiguration(API.API_KEY);
        call.enqueue(new Callback<TMDBImagesConfigurations>() {
            @Override
            public void onResponse(Call<TMDBImagesConfigurations> call, Response<TMDBImagesConfigurations> response) {
                // set image base url
                RETROFIT_IMAGE_BASE_URL = response.body().getImages().getBaseURL();

                imagesConfigurations.postValue(response.body());
            }

            @Override
            public void onFailure(Call<TMDBImagesConfigurations> call, Throwable t) {
                // TODO: what to do here?
            }
        });
    }

    private void loadMovies(@NonNull MovieCategory category) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<TMDBMoviesResponse> call;
        switch (category) {
            case TOP_RATED:
                call = api.getTopRatedMovies(API.API_KEY);
                break;
            case UPCOMING:
                call = api.getUpcomingMovies(API.API_KEY);
                break;
            case POPULAR:
            default:
                call = api.getPopularMovies(API.API_KEY);
                break;
        }

        call.enqueue(new Callback<TMDBMoviesResponse>() {
            @Override
            public void onResponse(Call<TMDBMoviesResponse> call, Response<TMDBMoviesResponse> response) {
                // fetched movies
                moviesList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<TMDBMoviesResponse> call, Throwable t) {
                // TODO: define what to do here
                String x = "error!";
            }
        });
    }

}