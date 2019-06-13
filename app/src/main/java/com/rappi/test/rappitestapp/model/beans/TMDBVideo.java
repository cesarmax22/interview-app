package com.rappi.test.rappitestapp.model.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class TMDBVideo implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public TMDBVideo createFromParcel(Parcel parcel) {
            return new TMDBVideo(parcel);
        }

        @Override
        public TMDBVideo[] newArray(int size) {
            return new TMDBVideo[size];
        }
    };

    private String id;
    private String key;
    private String name;
    private String type;

    public TMDBVideo(Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
        type = in.readString();
    }

    public TMDBVideo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(type);
    }
}
