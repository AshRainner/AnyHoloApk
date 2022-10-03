package com.example.anyholo.dbcon;

import com.example.anyholo.MemberModel.MemberModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DBcon {
    @GET("/dbcon")
    Call<MemberModel> getData();
}
