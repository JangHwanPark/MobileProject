package com.example.androidapplecation.network;
import com.example.androidapplecation.model.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    // (회원가입) 사용자 계정을 서버에 저장하는 POST 요청

    // 질문을 서버에 저장하는 POST 요청
    @POST("/post/question/save")
    Call<Void> saveQuestion(@Body Question question);

    // 질문과 자유게시판을 서버에서 가져오는 GET 요청
    @GET("/get/category/question")
    Call<List<Question>> getCategoryQuestion();

    @GET("/get/category/free-board")
    Call<List<Question>> getCategoryFreeBoard();
}
