package com.rappi.test.rappitestapp.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TMDBConfiguration {

    @SerializedName("base_url")
    private String baseURL;
    @SerializedName("secure_base_url")
    private String secureBaseURL;
    @SerializedName("poster_sizes")
    private ArrayList<String> posterSizes;

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getSecureBaseURL() {
        return secureBaseURL;
    }

    public void setSecureBaseURL(String secureBaseURL) {
        this.secureBaseURL = secureBaseURL;
    }

    public ArrayList<String> getPosterSizes() {
        return posterSizes;
    }

    public void setPosterSizes(ArrayList<String> posterSizes) {
        this.posterSizes = posterSizes;
    }
}
