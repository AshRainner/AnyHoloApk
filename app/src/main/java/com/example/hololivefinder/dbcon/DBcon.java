package com.example.hololivefinder.dbcon;

import com.example.hololivefinder.MemberModel.MemberModel;
import com.example.hololivefinder.MemberModel.MemberView;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DBcon {
    @GET("/dbcon")
    Call<MemberModel> getData();
}
