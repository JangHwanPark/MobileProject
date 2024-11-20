package com.example.androidapplecation.ui.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.androidapplecation.R;
import com.example.androidapplecation.data.model.Question;
import com.example.androidapplecation.data.network.ApiService;
import com.example.androidapplecation.data.network.RetrofitClient;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPostActivity extends BaseActivity {

    private EditText titleEditText;
    private EditText contentEditText;
    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        // EditText 초기화
        titleEditText = findViewById(R.id.editPostTitle);
        contentEditText = findViewById(R.id.editPostContent);

        // Spinner 초기화
        categorySpinner = findViewById(R.id.editPostInterestSpinner);

        // 전달된 Intent에서 데이터 받기
        Intent intent = getIntent();
        int qid = intent.getIntExtra("qid", -1);
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        Log.d(TAG, "qid" + qid);

        // 받은 데이터를 EditText에 설정
        if (title != null) {
            titleEditText.setText(title);
        }
        if (content != null) {
            contentEditText.setText(content);
        }

        // Spinner에 표시할 항목 설정
        ArrayAdapter<CharSequence> interestAdapter = ArrayAdapter.createFromResource(
                this, R.array.post_interest, android.R.layout.simple_spinner_item);
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(interestAdapter);

        // 체크 버튼 설정
        Button submitButton = findViewById(R.id.header_btn_check);
        submitButton.setVisibility(View.VISIBLE);
        submitButton.setOnClickListener(v -> handleClickSubmitButton(qid));
    }

    private void handleClickSubmitButton(int qid) {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();  // 선택된 카테고리 가져오기

        if (category.equals("질문답변") || category.equals("자유 게시판")) {
            // Question 객체 생성
            Question updatedQuestion = new Question(qid, title, content, category, new Date());

            // Retrofit으로 POST 요청 보내기
            sendQuestionToServer(qid, updatedQuestion);
            Log.d(TAG, "전송버튼 클릭");
        }

        // 액티비티 종료 후 이전화면으로 돌아가기
        finish();
    }

    private void sendQuestionToServer(int qid, Question question) {
        // Retrofit 클라이언트 생성
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token == null) {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "question: " + question.getId() + question.getTitle() + question.getContent() + question.getUpdatedAt());

        // 질문을 서버로 전송
        Call<Void> call = apiService.editQuestion(qid, question);  // qid와 수정된 Question 객체를 전송
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "서버에 질문이 성공적으로 수정되었습니다.");
                    // 성공적으로 저장됨
                    Toast.makeText(EditPostActivity.this, "질문이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "서버에 질문 수정 실패: " + response.errorBody());
                    Toast.makeText(EditPostActivity.this, "질문 수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(EditPostActivity.this, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
