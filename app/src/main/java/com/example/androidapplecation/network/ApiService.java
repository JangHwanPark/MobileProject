package com.example.androidapplecation.network;

import com.example.androidapplecation.model.HelloResponse;
import com.example.androidapplecation.model.Question;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    // 테스트 엔드포인트
    @GET("/hello")
    Call<HelloResponse> sayHello();

    // 질문을 서버에 저장하는 POST 요청
    @POST("/question/save")
    Call<Void> saveQuestion(@Body Question question);
}
