package com.example.androidapplecation.activity;

import com.example.androidapplecation.R;
import com.example.androidapplecation.model.Comment;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;

import android.content.SharedPreferences;
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

public class QuestionDetailActivity extends BaseActivity {
    private EditText editTextComment;
    private int uid; // 로그인한 사용자의 uid를 저장할 변수

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details); // XML 레이아웃 설정

        // SharedPreferences에서 uid 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        uid = sharedPreferences.getInt("uid", -1); // uid가 저장되지 않았을 경우 기본값 -1

        // 만약 uid가 유효하지 않다면, 사용자에게 로그인이 필요하다는 메시지를 띄우거나 다른 처리를 할 수 있습니다.
        if (uid == -1) {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

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

        // 댓글 전송 버튼 클릭 리스너 설정
        buttonSendComment.setOnClickListener(v -> {
            String commentText = editTextComment.getText().toString().trim();
            if (!commentText.isEmpty()) {
                Comment comment = new Comment();
                comment.setContent(commentText);
                comment.setUid(uid);  // 로그인한 사용자의 uid 설정
                comment.setQid(qid);  // 현재 질문의 qid 설정
                handleClickCommentToServer(comment);
            } else {
                Toast.makeText(this, "댓글을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        // 데이터 설정
        textViewTitle.setText(title != null ? title : "No Title");
        textViewContent.setText(content != null ? content : "No Content");
    }

    // 댓글 등록
    private void handleClickCommentToServer(Comment comment) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.createComment(comment);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(QuestionDetailActivity.this, "Comment posted successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("QuestionDetailActivity", "Comment posted successfully.");
                } else {
                    Toast.makeText(QuestionDetailActivity.this, "Failed to post comment: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("QuestionDetailActivity", "Failed to post comment. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(QuestionDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(QuestionDetailActivity.this, "Comment updated successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("QuestionDetailActivity", "Comment updated successfully.");
                } else {
                    Toast.makeText(QuestionDetailActivity.this, "Failed to update comment: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("QuestionDetailActivity", "Failed to update comment. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(QuestionDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(QuestionDetailActivity.this, "Comment deleted successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("QuestionDetailActivity", "Comment deleted successfully.");
                } else {
                    Toast.makeText(QuestionDetailActivity.this, "Failed to delete comment: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("QuestionDetailActivity", "Failed to delete comment. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(QuestionDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("QuestionDetailActivity", "Error deleting comment: " + t.getMessage(), t);
            }
        });
    }
}
