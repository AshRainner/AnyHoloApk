package com.anyholo.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Model implements Serializable {
    @SerializedName("Member")
    private ArrayList<MemberView> member = null;

    @SerializedName("Kirinuki")
    private ArrayList<KirinukiView> Kirinuki = null;

    @SerializedName("Tweet")
    private ArrayList<TweetView> Tweet = null;

    @SerializedName("Version")
    private String version;

    @SerializedName("UpdateString")
    private String updateString;

    public String getUpdateString() {
        return updateString;
    }

    public void setUpdateString(String updateString) {
        this.updateString = updateString;
    }

    public ArrayList<MemberView> getMemberList() {
        return member;
    }

    public void setMemberList(ArrayList<MemberView> member) {
        this.member = member;
    }

    public ArrayList<KirinukiView> getVidoes() {
        return Kirinuki;
    }

    public void setVidoes(ArrayList<KirinukiView> Kirinuki) {
        this.Kirinuki = Kirinuki;
    }

    public ArrayList<TweetView> getTweet() {
        return Tweet;
    }

    public void setTweet(ArrayList<TweetView> tweet) {
        Tweet = tweet;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
