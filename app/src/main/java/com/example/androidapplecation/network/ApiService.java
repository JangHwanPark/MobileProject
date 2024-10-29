package com.example.androidapplecation.network;
import com.example.androidapplecation.model.LoginResponse;
import com.example.androidapplecation.model.Question;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.model.Comment;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    // (회원가입) 사용자 계정을 서버에 저장하는 POST 요청
    @POST("/post/user/register")
    Call<Void> registerUser(@Body User user);

    // 질문을 서버에 저장하는 POST 요청
    @POST("/post/question/save")
    Call<Void> saveQuestion(@Body Question question);

    // 질문과 자유게시판을 서버에서 가져오는 GET 요청
    @GET("/get/category/question")
    Call<List<Question>> getCategoryQuestion();

    @GET("/get/category/free-board")
    Call<List<Question>> getCategoryFreeBoard();

    @GET("/get/user/list")
    Call<List<User>> getUserList();

    // 로그인
    @POST("/api/login")
    Call<LoginResponse> loginUser(@Body Map<String, String> user);

    @POST("/post/comment/create")
    Call<Void> createComment(@Body Comment comment);

    @POST("/post/comment/update/{commentId}")
    Call<Void> updateComment(@Path("cid") int commentId, @Body Comment comment);

    @POST("/post/comment/delete/{commentId}")
    Call<Void> deleteComment(@Path("cid") int commentId);
}
