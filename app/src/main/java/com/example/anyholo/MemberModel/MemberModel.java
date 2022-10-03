package com.example.anyholo.MemberModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MemberModel implements Serializable {
    @SerializedName("Member")
    private ArrayList<MemberView> member = null;

    public ArrayList<MemberView> getMemberList() {
        return member;
    }

    public void setMemberList(ArrayList<MemberView> member) {
        this.member = member;
    }
}
