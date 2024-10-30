package com.example.androidapplecation.network;
import com.example.androidapplecation.model.LoginResponse;
import com.example.androidapplecation.model.Question;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.model.Comment;
import com.example.androidapplecation.wrapper.ResWrapper;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    // Question
    @POST("/api/v1/question/post/save")
    Call<Void> saveQuestion(@Header("Authorization") String token, @Body Question question);

    @GET("/api/v1/question/get/category/question")
    Call<List<Question>> getCategoryQuestion();

    @GET("/api/v1/question/get/category/free-board")
    Call<List<Question>> getCategoryFreeBoard();

    // Comment
    @POST("/api/v1/comment/post/create")
    Call<Void> createComment(@Body Comment comment);

    @POST("/api/v1/comment/post/update/id")
    Call<Void> updateComment(@Path("cid") int commentId, @Body Comment comment);

    @POST("/api/v1/comment/post/delete/id")
    Call<Void> deleteComment(@Path("cid") int commentId);

    // User
    @GET("/api/v1/user/get/info")
    Call<ResWrapper<User>> getUserInfo(@Header("Authorization") String token);

    @POST("/api/v1/user/post/register")
    Call<Void> registerUser(@Body User user);

    @GET("/api/v1/user/api/get/select-all")
    Call<List<User>> getAllUsers();

    // 로그인
    @POST("/api/login")
    Call<LoginResponse> loginUser(@Body Map<String, String> user);

    // Answer
}
