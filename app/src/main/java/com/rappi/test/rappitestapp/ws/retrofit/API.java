package com.rappi.test.rappitestapp.ws.retrofit;

import com.rappi.test.rappitestapp.beans.TMDBImagesConfigurations;
import com.rappi.test.rappitestapp.beans.TMDBMovie;
import com.rappi.test.rappitestapp.beans.TMDBMoviesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    String BASE_URL = "https://api.themoviedb.org/3/";
    // TODO: there are better ways to store api_key
    String API_KEY = "3454517d63aea88f2cbc53a050f126cf";

    @GET("movie/popular")
    Call<TMDBMoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<TMDBMoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);


    @GET("movie/upcoming")
    Call<TMDBMoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("configuration")
    Call<TMDBImagesConfigurations> getImagesConfiguration(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}?append_to_response=videos")
    Call<TMDBMovie> getMovieDetail(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

}
