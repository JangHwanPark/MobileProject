package com.example.androidapplecation.activity;

import static android.content.ContentValues.TAG;

import com.example.androidapplecation.R;
import com.example.androidapplecation.model.Comment;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;

import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends BaseActivity {
    private EditText editTextComment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details); // XML 레이아웃 설정

        // 버튼 클릭 시 뒤로 가기
        setupUndoButton();

        // XML과 연결
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewContent = findViewById(R.id.textViewContent);

        // 댓글 입력 필드와 전송 버튼 연결
        editTextComment = findViewById(R.id.editTextComment);
        Button buttonSendComment = findViewById(R.id.buttonSendComment);

        // Intent로부터 전달된 데이터 받기
        int qid = getIntent().getIntExtra("qid", -1);
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        Log.d(TAG, "qid: " + qid);

        // 댓글 전송 버튼 클릭 리스너 설정
        buttonSendComment.setOnClickListener(v -> handleClickListenerToSubmitBtn(qid));

        // 데이터 설정
        textViewTitle.setText(title != null ? title : "No Title");
        textViewContent.setText(content != null ? content : "No Content");
    }

    // 전송 버튼 이벤트
    private void handleClickListenerToSubmitBtn(int qid) {
        String commentText = editTextComment.getText().toString().trim();
        if (!commentText.isEmpty()) {
            Comment comment = new Comment();
            comment.setContent(commentText);
            comment.setQid(qid);  // 현재 질문의 qid 설정
            handleClickCommentToServer(comment);
        } else {
            Toast.makeText(this, "댓글을 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    // 댓글 등록
    private void handleClickCommentToServer(Comment comment) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        Call<Void> call = apiService.createComment(token,comment);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PostDetailActivity.this, "Comment posted successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("QuestionDetailActivity", "Comment posted successfully.");
                } else {
                    Toast.makeText(PostDetailActivity.this, "Failed to post comment: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("QuestionDetailActivity", "Failed to post comment. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(PostDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("QuestionDetailActivity", "Error posting comment: " + t.getMessage(), t);
            }
        });
    }

    // 댓글 수정
    private void handleUpdateCommentToServer(int commentId, Comment updatedComment) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.updateComment(commentId, updatedComment);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PostDetailActivity.this, "Comment updated successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("QuestionDetailActivity", "Comment updated successfully.");
                } else {
                    Toast.makeText(PostDetailActivity.this, "Failed to update comment: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("QuestionDetailActivity", "Failed to update comment. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(PostDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("QuestionDetailActivity", "Error updating comment: " + t.getMessage(), t);
            }
        });
    }

    // 댓글 삭제
    private void handleDeleteCommentFromServer(int commentId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.deleteComment(commentId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PostDetailActivity.this, "Comment deleted successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("QuestionDetailActivity", "Comment deleted successfully.");
                } else {
                    Toast.makeText(PostDetailActivity.this, "Failed to delete comment: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("QuestionDetailActivity", "Failed to delete comment. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(PostDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("QuestionDetailActivity", "Error deleting comment: " + t.getMessage(), t);
            }
        });
    }
}
