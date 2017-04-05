
package com.example.okhttp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentDetails implements Parcelable{

    @SerializedName("videoId")
    @Expose
    private String videoId;
    @SerializedName("videoPublishedAt")
    @Expose
    private String videoPublishedAt;

    protected ContentDetails(Parcel in) {
        videoId = in.readString();
        videoPublishedAt = in.readString();
    }

    public static final Creator<ContentDetails> CREATOR = new Creator<ContentDetails>() {
        @Override
        public ContentDetails createFromParcel(Parcel in) {
            return new ContentDetails(in);
        }

        @Override
        public ContentDetails[] newArray(int size) {
            return new ContentDetails[size];
        }
    };

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoPublishedAt() {
        return videoPublishedAt;
    }

    public void setVideoPublishedAt(String videoPublishedAt) {
        this.videoPublishedAt = videoPublishedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoId);
        dest.writeString(videoPublishedAt);
    }
}
