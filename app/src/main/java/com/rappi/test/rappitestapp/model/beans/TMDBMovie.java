package com.rappi.test.rappitestapp.model.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Class representing response for a specific movie.
 */
public class TMDBMovie implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public TMDBMovie createFromParcel(Parcel parcel) {
            return new TMDBMovie(parcel);
        }

        @Override
        public TMDBMovie[] newArray(int size) {
            return new TMDBMovie[size];
        }
    };

    private boolean adult;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    private int id;
    private String title;
    @SerializedName("original_title")
    private String originalTitle;
    private float popularity;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("poster_path")
    private String posterPath;
    private TMDBVideos videos;

    public TMDBMovie(Parcel in) {
        adult = in.readByte() != 0;
        overview = in.readString();
        releaseDate = in.readString();
        id = in.readInt();
        title = in.readString();
        originalTitle = in.readString();
        popularity = in.readFloat();
        voteCount = in.readInt();
        voteAverage = in.readFloat();
        posterPath = in.readString();
        videos = in.readParcelable(TMDBVideos.class.getClassLoader());
    }

    public TMDBMovie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public TMDBVideos getVideos() {
        return videos;
    }

    public void setVideos(TMDBVideos videos) {
        this.videos = videos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(originalTitle);
        parcel.writeFloat(popularity);
        parcel.writeInt(voteCount);
        parcel.writeFloat(voteAverage);
        parcel.writeString(posterPath);
        parcel.writeParcelable(videos, flags);
    }
}
