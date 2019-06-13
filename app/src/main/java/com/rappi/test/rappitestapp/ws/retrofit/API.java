package com.rappi.test.rappitestapp.ws.retrofit;

import com.rappi.test.rappitestapp.model.beans.TMDBImagesConfigurations;
import com.rappi.test.rappitestapp.model.beans.TMDBMovie;
import com.rappi.test.rappitestapp.model.beans.TMDBMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit API interface.
 */
public interface API {

    // "themoviedb" base url. in a real app this key should be stored in a more secure/dynamic way! gradle.properties for example
    String BASE_URL = "https://api.themoviedb.org/3/";
    // "themoviedb" api key. in a real app this key should be stored in a more secure/dynamic way! gradle.properties for example
    String API_KEY = "3454517d63aea88f2cbc53a050f126cf";

    /**
     * Gets list of popular movies.
     * @param apiKey
     * @return
     */
    @GET("movie/popular")
    Call<TMDBMoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    /**
     * Gets list of top reated movies.
     * @param apiKey
     * @return
     */
    @GET("movie/top_rated")
    Call<TMDBMoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    /**
     * Gets list of upcoming movies.
     * @param apiKey
     * @return
     */
    @GET("movie/upcoming")
    Call<TMDBMoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    /**
     * Gets configuration to later on retrieve for images.
     * @param apiKey
     * @return
     */
    @GET("configuration")
    Call<TMDBImagesConfigurations> getImagesConfiguration(@Query("api_key") String apiKey);

    /**
     * Gets details of a given movie id.
     * @param movieId
     * @param apiKey
     * @return
     */
    @GET("movie/{movie_id}?append_to_response=videos")
    Call<TMDBMovie> getMovieDetail(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

}
