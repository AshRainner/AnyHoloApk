package com.example.anyholo.dbcon;

import com.example.anyholo.Model.Model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DBcon {
    @GET("/dbcon")
    Call<Model> getData();
}
