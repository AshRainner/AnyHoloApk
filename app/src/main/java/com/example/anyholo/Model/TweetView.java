package com.example.anyholo.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TweetView implements Serializable {
    @SerializedName("tweetId")
    private String tweetId;
    @SerializedName("writeUserName")
    private String writeUserName;
    @SerializedName("userId")
    private String userId;
    @SerializedName("userProfileUrl")
    private String userProfileUrl;
    @SerializedName("twitContent")
    private String twitContent;
    @SerializedName("twitType")
    private String twitType;
    @SerializedName("nextTwitId")
    private String nextTwitId;
    @SerializedName("mediaType")
    private String mediaType;
    @SerializedName("mediaUrl")
    private String mediaUrl;
    @SerializedName("writeDate")
    private String writeDate;

    public TweetView(String tweetId, String writeUserName, String userId, String userProfileUrl, String twitContent, String twitType, String nextTwitId, String mediaType, String mediaUrl, String writeDate) {
        this.tweetId = tweetId;
        this.writeUserName = writeUserName;
        this.userId = userId;
        this.userProfileUrl = userProfileUrl;
        this.twitContent = twitContent;
        this.twitType = twitType;
        this.nextTwitId = nextTwitId;
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.writeDate = writeDate;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getWriteUserName() {
        return writeUserName;
    }

    public void setWriteUserName(String writeUserName) {
        this.writeUserName = writeUserName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public String getTwitContent() {
        return twitContent;
    }

    public void setTwitContent(String twitContent) {
        this.twitContent = twitContent;
    }

    public String getTwitType() {
        return twitType;
    }

    public void setTwitType(String twitType) {
        this.twitType = twitType;
    }

    public String getNextTwitId() {
        return nextTwitId;
    }

    public void setNextTwitId(String nextTwitId) {
        this.nextTwitId = nextTwitId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }
}
