package com.example.androidapplecation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidapplecation.R;
import com.example.androidapplecation.model.Question;
import com.example.androidapplecation.repository.QuestionRepository;

import java.util.Date;

public class CreatePostActivity extends BaseActivity {

    private EditText titleEditText;
    private EditText contentEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // 버튼 클릭 시 뒤로 가기
        setupUndoButton();

        // 체크 버튼 설정
        submitButton = findViewById(R.id.header_btn_check);
        submitButton.setVisibility(View.VISIBLE);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 제목 내용
                String title = titleEditText.getText().toString();
                String content = titleEditText.getText().toString();

                // 새로운 Question 객체 생성
                Question newQuestion = new Question(
                        "id" + (QuestionRepository.getInstance().getQuestionList().size() + 1), // 새로운 ID
                        title,  // 제목
                        content, // 내용
                        "test user",  // 작성자 (임의로 설정)
                        "qid" + (QuestionRepository.getInstance().getQuestionList().size() + 1), // 질문 ID
                        new Date(),  // 생성 날짜
                        new Date()   // 수정 날짜
                );

                // QuestionRepository 에 새로운 질문 추가
                QuestionRepository.getInstance().addQuestion(newQuestion);

                // 액티비티 종료 후 이전화면으로 돌아가기
                finish();
            }
        });
    }
}
