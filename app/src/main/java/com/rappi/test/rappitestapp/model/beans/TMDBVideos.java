package com.rappi.test.rappitestapp.model.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class TMDBVideos implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public TMDBVideos createFromParcel(Parcel parcel) {
            return new TMDBVideos(parcel);
        }

        @Override
        public TMDBVideos[] newArray(int size) {
            return new TMDBVideos[size];
        }
    };

    private TMDBVideo[] results;

    public TMDBVideo[] getResults() {
        return results;
    }

    public void setResults(TMDBVideo[] results) {
        this.results = results;
    }

    public TMDBVideos(Parcel in) {
        results = (TMDBVideo[]) in.readParcelableArray(TMDBVideo.class.getClassLoader());
    }

    public TMDBVideos() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelableArray(results, flags);
    }
}
