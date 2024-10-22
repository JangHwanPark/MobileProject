package com.example.androidapplecation.activity;

import static android.content.ContentValues.TAG;

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
import com.example.androidapplecation.model.Question;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends BaseActivity {

    private EditText titleEditText;
    private EditText contentEditText;
    private Spinner categorySpinner;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // EditText 초기화
        titleEditText = findViewById(R.id.question_title);
        contentEditText = findViewById(R.id.question_content);

        // Spinner 초기화
        categorySpinner = findViewById(R.id.category_spinner);

        // Spinner에 표시할 항목 설정
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.post_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        Log.d(TAG, "categorySpinner: " + String.valueOf(categorySpinner));

        // 체크 버튼 설정
        submitButton = findViewById(R.id.header_btn_check);
        submitButton.setVisibility(View.VISIBLE);
        submitButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String content = contentEditText.getText().toString();
            String category = categorySpinner.getSelectedItem().toString();  // 선택된 카테고리 가져오기

            if (category.equals("질문하기")) {
                // Question 객체 생성
                Question newQuestion = new Question(null, 1, title, content, category, new Date(), new Date());

                // Retrofit으로 POST 요청 보내기
                sendQuestionToServer(newQuestion);

            } else if (category.equals("자유 게시판")) {
                // 자유 게시판 로직 처리
            }

            // 액티비티 종료 후 이전화면으로 돌아가기
            finish();
        });
    }

    private void sendQuestionToServer(Question question) {
        // Retrofit 클라이언트 생성
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // 질문을 서버로 전송
        Call<Void> call = apiService.saveQuestion(question);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "서버에 질문이 성공적으로 저장되었습니다.");
                    // 성공적으로 저장됨
                    Toast.makeText(
                            CreatePostActivity.this,
                            "질문이 서버에 저장되었습니다.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "서버에 질문 저장 실패: " + response.errorBody());
                    // 저장 실패
                    Toast.makeText(
                            CreatePostActivity.this,
                            "질문 저장에 실패했습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // 네트워크 오류 처리
                Toast.makeText(
                        CreatePostActivity.this,
                        "서버와의 통신에 실패했습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
