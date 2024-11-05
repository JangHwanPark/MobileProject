package com.example.androidapplecation.data.network;
import com.example.androidapplecation.data.model.LoginResponse;
import com.example.androidapplecation.data.model.Question;
import com.example.androidapplecation.data.model.User;
import com.example.androidapplecation.data.model.Comment;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    String header = "Authorization";

    // Question
    @POST("/api/v1/question/post/save")
    Call<Void> saveQuestion(@Header(header) String token, @Body Question question);

    @GET("/api/v1/question/get/category/question")
    Call<List<Question>> getCategoryQuestion();

    @GET("/api/v1/question/get/category/free-board")
    Call<List<Question>> getCategoryFreeBoard();

    @POST("/api/v1/question/post/{qid}/update")
    Call<Void> editQuestion(String token, int postId, String newContent);

    @POST("/api/v1/question/post/{qid}/delete")
    Call<Void> deleteQuestion(String token, int postId);

    // Comment
    @GET("/api/v1/comment/get/{qid}")
    Call<List<Comment>> getComments(@Path("qid") int qid);

    @POST("/api/v1/comment/post/create")
    Call<Void> createComment(@Header(header) String token, @Body Comment comment);

    @POST("/api/v1/comment/post/update")
    Call<Void> updateComment(@Body Comment comment);

    @POST("/api/v1/comment/post/delete/{cid}")
    Call<Void> deleteComment(@Path("cid") int commentId);

    // User
    @GET("/api/v1/user/get/info")
    Call<ResWrapper<User>> getUserInfo(@Header(header) String token);

    @POST("/api/v1/user/post/register")
    Call<Void> registerUser(@Body User user);

    @GET("/api/v1/user/get/all/users")
    Call<List<User>> getAllUsers();

    // 로그인
    @POST("/api/login")
    Call<LoginResponse> loginUser(@Body Map<String, String> user);

    // Answer
}
