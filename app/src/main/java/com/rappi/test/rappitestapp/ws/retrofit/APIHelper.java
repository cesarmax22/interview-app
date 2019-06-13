package com.rappi.test.rappitestapp.ws.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHelper {

    // cache size. this value should be somewhere else so to be build customizable
    private static final long CACHE_SIZE = 5 * 1024 * 1024;

    /**
     * It returns the {@link API} for retrofit http calls.
     * @param context
     * @return
     */
    public static API getAPIClient(@NonNull Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHTTPClient(context))
                .build();

        return retrofit.create(API.class);
    }

    /**
     * It builds {@link OkHttpClient} to be used by clients.
     * @param context
     * @return
     */
    private static OkHttpClient getHTTPClient(@NonNull final Context context) {
        final Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);

        OkHttpClient client = new OkHttpClient.Builder()
                                    .cache(cache)
                                    .addInterceptor(new Interceptor() {
                                        @Override
                                        public Response intercept(Chain chain) throws IOException {
                                            Request request;
                                            if (hasNetwork(context)) {
                                                // available internet connection
                                                // get cache stored 5 seconds ago maximum
                                                request = chain.request().newBuilder().header("Cache-Control", "public, max-age=" + 5).build();
                                            } else {
                                                // no internet connection
                                                // get cache stored 7 days ago maximum
                                                request = chain.request().newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                                            }

                                            return chain.proceed(request);
                                        }
                                    })
                                    .build();

        return client;
    }

    private static final boolean hasNetwork(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
