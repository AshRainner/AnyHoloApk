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
    @SerializedName("tweetContent")
    private String tweetContent;
    @SerializedName("tweetType")
    private String tweetType;
    @SerializedName("nextTweetId")
    private String nextTweetId;
    @SerializedName("mediaType")
    private String mediaType;
    @SerializedName("mediaUrl")
    private String mediaUrl;
    @SerializedName("writeDate")
    private String writeDate;
    @SerializedName("nextTweet")
    private TweetView nextTweet;

    private String retweetText;

    public TweetView(String tweetId, String writeUserName, String userId, String userProfileUrl, String tweetContent, String tweetType, String nextTweetId, String mediaType, String mediaUrl, String writeDate, TweetView nextTweet, String retweetText) {
        this.tweetId = tweetId;
        this.writeUserName = writeUserName;
        this.userId = userId;
        this.userProfileUrl = userProfileUrl;
        this.tweetContent = tweetContent;
        this.tweetType = tweetType;
        this.nextTweetId = nextTweetId;
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.writeDate = writeDate;
        this.nextTweet = nextTweet;
        this.retweetText = retweetText;
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

    public String getTweetContent() {
        return tweetContent;
    }

    public void setTweetContent(String tweetContent) {
        this.tweetContent = tweetContent;
    }

    public String getTweetType() {
        return tweetType;
    }

    public void setTweetType(String tweetType) {
        this.tweetType = tweetType;
    }

    public String getNextTweetId() {
        return nextTweetId;
    }

    public void setNextTweetId(String nextTweetId) {
        this.nextTweetId = nextTweetId;
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

    public String getRetweetText() {
        return retweetText;
    }

    public void setRetweetText(String retweetText) {
        this.retweetText = retweetText;
    }

    public TweetView getNextTweet() {
        return nextTweet;
    }

    public void setNextTweet(TweetView nextTweet) {
        this.nextTweet = nextTweet;
    }
}