package com.example.anyholo.dbcon;

import com.example.anyholo.Model.Model;
import com.example.anyholo.ProfileModel.ProfileModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DBcon {
    @GET("/dbcon")
    Call<Model> getData();
    @GET("/dbcon/Live")
    Call<Model> getLiveData();
    @GET("/dbcon/Tweet")
    Call<Model> getTweetData(@Query("TPage") String tPage);
    @GET("/dbcon/Kirinuki")
    Call<Model> getKirinukiData(@Query("KPage") String kPage);
}
