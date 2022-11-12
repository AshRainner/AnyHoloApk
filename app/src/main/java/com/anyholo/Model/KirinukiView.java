package com.anyholo.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KirinukiView implements Serializable {
    @SerializedName("youtubeUrl")
    private String youtubeUrl;
    @SerializedName("channelName")
    private String channelName;
    @SerializedName("thumnailUrl")
    private String thumnailUrl;
    @SerializedName("videoTitle")
    private String videoTitle;
    @SerializedName("tag")
    private String tag;
    @SerializedName("uploadTime")
    private String uploadTime;

    public KirinukiView(String youtubeUrl, String channelName, String thumnailUrl, String videoTitle, String tag, String uploadTime) {
        this.youtubeUrl = youtubeUrl;
        this.channelName = channelName;
        this.thumnailUrl = thumnailUrl;
        this.videoTitle = videoTitle;
        this.tag = tag;
        this.uploadTime = uploadTime;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getThumnailUrl() {
        return thumnailUrl;
    }

    public void setThumnailUrl(String thumnailUrl) {
        this.thumnailUrl = thumnailUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}
