package com.example.androidapplecation.network;

import com.example.androidapplecation.model.HelloResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/hello")
    Call<HelloResponse> sayHello();
}
