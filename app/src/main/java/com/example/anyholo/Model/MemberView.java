package com.example.anyholo.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MemberView implements Serializable {
    @SerializedName("memberName")
    private String memberName;
    @SerializedName("country")
    private String country;
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("introduceText")
    private String introduceText;
    @SerializedName("onAir")
    private String onAir;
    @SerializedName("onairTitle")
    private String onairTitle;
    @SerializedName("onAirThumnailsUrl")
    private String onAirThumnailsUrl;
    @SerializedName("onAirViedoUrl")
    private String onAirViedoUrl;
    @SerializedName("channelId")
    private String channelId;
    @SerializedName("twitterurl")
    private String twitterurl;
    @SerializedName("hololiveUrl")
    private String hololiveUrl;
    @SerializedName("num")
    private Integer num;

    public MemberView(String memberName, String country, String imageUrl, String introduceText, String onAir, String onairTitle, String onAirThumnailsUrl, String onAirViedoUrl, String channelId, String twitterurl, String hololiveUrl, Integer num) {
        this.memberName = memberName;
        this.country = country;
        this.imageUrl = imageUrl;
        this.introduceText = introduceText;
        this.onAir = onAir;
        this.onairTitle = onairTitle;
        this.onAirThumnailsUrl = onAirThumnailsUrl;
        this.onAirViedoUrl = onAirViedoUrl;
        this.channelId = channelId;
        this.twitterurl = twitterurl;
        this.hololiveUrl = hololiveUrl;
        this.num = num;
    }

    public String getTwitterurl() {
        return twitterurl;
    }

    public void setTwitterurl(String twitterurl) {
        this.twitterurl = twitterurl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getHololiveUrl() {
        return hololiveUrl;
    }

    public void setHololiveUrl(String hololiveUrl) {
        this.hololiveUrl = hololiveUrl;
    }

    public String getIntroduceText() {
        return introduceText;
    }

    public void setIntroduceText(String introduceText) {
        this.introduceText = introduceText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getOnAirThumnailsUrl() {
        return onAirThumnailsUrl;
    }

    public void setOnAirThumnailsUrl(String onAirThumnailsUrl) {
        this.onAirThumnailsUrl = onAirThumnailsUrl;
    }

    public String getOnAirViedoUrl() {
        return onAirViedoUrl;
    }

    public void setOnAirViedoUrl(String onAirViedoUrl) {
        this.onAirViedoUrl = onAirViedoUrl;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

}
