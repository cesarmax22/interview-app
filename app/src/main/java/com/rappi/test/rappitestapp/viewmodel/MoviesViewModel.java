package com.rappi.test.rappitestapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.rappi.test.rappitestapp.model.beans.MovieCategory;
import com.rappi.test.rappitestapp.model.beans.TMDBImagesConfigurations;
import com.rappi.test.rappitestapp.model.beans.TMDBMovie;
import com.rappi.test.rappitestapp.model.beans.TMDBMoviesResponse;
import com.rappi.test.rappitestapp.ws.retrofit.API;
import com.rappi.test.rappitestapp.ws.retrofit.APIHelper;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * View model class in charge of loading data and notifying about its changes related to movies.
 *
 * Please note that this class extends {@link AndroidViewModel} because we need an
 * explicit reference to the application context.
 */
public class MoviesViewModel extends AndroidViewModel {

    // we will fetch this data async
    protected MutableLiveData<TMDBMoviesResponse> moviesList;

    private Map<Integer, MutableLiveData<TMDBMovie>> moviesDetailsMap = new HashMap<>();

    protected MovieCategory movieCategory;

    /**
     * Image base url to retrieve images through retrofit. This is fetched from server one time only.
     */
    public static String RETROFIT_IMAGE_BASE_URL = null;

    private MutableLiveData<TMDBImagesConfigurations> imagesConfigurations;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
    }

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
    // TODO: not being used so far
    public void refreshMovies() {
        loadMovies(movieCategory);
    }

    private void loadMovieDetails(final int movieId) {
        API api = APIHelper.getAPIClient(getApplication().getApplicationContext());
        Call<TMDBMovie> call = api.getMovieDetail(movieId, API.API_KEY);
        call.enqueue(new Callback<TMDBMovie>() {
            @Override
            public void onResponse(Call<TMDBMovie> call, Response<TMDBMovie> response) {
                moviesDetailsMap.get(movieId).postValue(response.body());
            }

            @Override
            public void onFailure(Call<TMDBMovie> call, Throwable t) {
                // TODO: create a wrapper class that hold data AND status code (for errors i.e.)
            }
        });
    }

    private void loadImagesConfigurations() {
        API api = APIHelper.getAPIClient(getApplication().getApplicationContext());
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
                // TODO: create a wrapper class that hold data AND status code (for errors i.e.)
            }
        });
    }

    private void loadMovies(@NonNull MovieCategory category) {
        API api = APIHelper.getAPIClient(getApplication().getApplicationContext());
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
                // TODO: create a wrapper class that hold data AND status code (for errors i.e.)
            }
        });
    }

}