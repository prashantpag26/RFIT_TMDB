
package com.example.okhttp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResourceId implements Parcelable{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("videoId")
    @Expose
    private String videoId;

    protected ResourceId(Parcel in) {
        kind = in.readString();
        videoId = in.readString();
    }

    public static final Creator<ResourceId> CREATOR = new Creator<ResourceId>() {
        @Override
        public ResourceId createFromParcel(Parcel in) {
            return new ResourceId(in);
        }

        @Override
        public ResourceId[] newArray(int size) {
            return new ResourceId[size];
        }
    };

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(videoId);
    }
}
