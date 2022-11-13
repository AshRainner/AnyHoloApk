package com.anyholo.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MemberView implements Serializable {
    @SerializedName("memberName")
    private String memberName;
    @SerializedName("country")
    private String country;
    @SerializedName("profileUrl")
    private String profileUrl;
    @SerializedName("searchName")
    private String searchName;
    @SerializedName("onAir")
    private String onAir;
    @SerializedName("onAirTitle")
    private String onairTitle;
    @SerializedName("onAirThumnailsUrl")
    private String onAirThumnailsUrl;
    @SerializedName("onAirVideoUrl")
    private String onAirVideoUrl;
    @SerializedName("channelId")
    private String channelId;
    @SerializedName("twitterUrl")
    private String twitterUrl;
    @SerializedName("hololiveUrl")
    private String hololiveUrl;
    @SerializedName("enName")
    private String enName;

    public MemberView(String memberName, String country, String profileUrl, String searchName, String onAir, String onairTitle, String onAirThumnailsUrl, String onAirVideoUrl, String channelId, String twitterUrl, String hololiveUrl, String enName) {
        this.memberName = memberName;
        this.country = country;
        this.profileUrl = profileUrl;
        this.searchName = searchName;
        this.onAir = onAir;
        this.onairTitle = onairTitle;
        this.onAirThumnailsUrl = onAirThumnailsUrl;
        this.onAirVideoUrl = onAirVideoUrl;
        this.channelId = channelId;
        this.twitterUrl = twitterUrl;
        this.hololiveUrl = hololiveUrl;
        this.enName = enName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getOnAir() {
        return onAir;
    }

    public void setOnAir(String onAir) {
        this.onAir = onAir;
    }

    public String getOnairTitle() {
        return onairTitle;
    }

    public void setOnairTitle(String onairTitle) {
        this.onairTitle = onairTitle;
    }

    public String getOnAirThumnailsUrl() {
        return onAirThumnailsUrl;
    }

    public void setOnAirThumnailsUrl(String onAirThumnailsUrl) {
        this.onAirThumnailsUrl = onAirThumnailsUrl;
    }

    public String getOnAirVideoUrl() {
        return onAirVideoUrl;
    }

    public void setOnAirVideoUrl(String onAirVideoUrl) {
        this.onAirVideoUrl = onAirVideoUrl;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTwitterurl() {
        return twitterUrl;
    }

    public void setTwitterurl(String twitterurl) {
        this.twitterUrl = twitterurl;
    }

    public String getHololiveUrl() {
        return hololiveUrl;
    }

    public void setHololiveUrl(String hololiveUrl) {
        this.hololiveUrl = hololiveUrl;
    }
}
