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
    @SerializedName("prevTweetId")
    private String prevTweetId;
    @SerializedName("mediaType")
    private String mediaType;
    @SerializedName("mediaUrl")
    private String mediaUrl;
    @SerializedName("writeDate")
    private String writeDate;
    @SerializedName("prevTweet")
    private TweetView prevTweet;
    private String retweetUser;

    public TweetView(String tweetId, String writeUserName, String userId, String userProfileUrl, String tweetContent, String tweetType, String prevTweetId, String mediaType, String mediaUrl, String writeDate, TweetView prevTweet, String retweetUser) {
        this.tweetId = tweetId;
        this.writeUserName = writeUserName;
        this.userId = userId;
        this.userProfileUrl = userProfileUrl;
        this.tweetContent = tweetContent;
        this.tweetType = tweetType;
        this.prevTweetId = prevTweetId;
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.writeDate = writeDate;
        this.prevTweet = prevTweet;
        this.retweetUser = retweetUser;
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

    public String getPrevTweetId() {
        return prevTweetId;
    }

    public void setprevTweetId(String prevTweetId) {
        this.prevTweetId = prevTweetId;
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

    public String getRetweetUser() {
        return retweetUser;
    }

    public void setRetweetUser(String retweetText) {
        this.retweetUser = retweetText;
    }

    public TweetView getPrevTweet() {
        return prevTweet;
    }

    public void setPrevTweet(TweetView prevTweet) {
        this.prevTweet = prevTweet;
    }
}