package com.example.anyholo.Model;

import com.example.anyholo.Model.MemberView;
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
}
