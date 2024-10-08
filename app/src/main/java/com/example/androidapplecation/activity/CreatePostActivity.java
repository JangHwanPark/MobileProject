package com.example.androidapplecation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.androidapplecation.R;
import com.example.androidapplecation.model.Board;
import com.example.androidapplecation.model.Question;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.repository.BoardRepository;
import com.example.androidapplecation.repository.QuestionRepository;

import java.util.Date;

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

        // 체크 버튼 설정
        submitButton = findViewById(R.id.header_btn_check);
        submitButton.setVisibility(View.VISIBLE);
        submitButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String content = contentEditText.getText().toString();
            String category = categorySpinner.getSelectedItem().toString();  // 선택된 카테고리 가져오기

            if (category.equals("질문하기")) {
                // Question 객체 생성 및 저장
                Question newQuestion = new Question(
                        "id" + (QuestionRepository.getInstance().getQuestionList().size() + 1), // 새로운 ID
                        title,  // 제목
                        content, // 내용
                        "test user",  // 작성자 (임의로 설정)
                        "qid" + (QuestionRepository.getInstance().getQuestionList().size() + 1), // 질문 ID
                        new Date(),  // 생성 날짜
                        new Date()   // 수정 날짜
                );

                // QuestionRepository에 저장
                QuestionRepository.getInstance().addQuestion(newQuestion);

                Toast.makeText(CreatePostActivity.this, "질문이 저장되었습니다.", Toast.LENGTH_SHORT).show();

            } else if (category.equals("자유 게시판")) {
                // Board 객체 생성 및 저장
                Board newBoard = new Board(
                        "test user",  // 작성자 (임의로 설정)
                        title,  // 제목
                        content,  // 내용
                        new Date()  // 작성일
                );

                // BoardRepository에 저장
                BoardRepository.getInstance().addBoard(newBoard);

                Toast.makeText(CreatePostActivity.this, "게시물이 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }

            // 액티비티 종료 후 이전화면으로 돌아가기
            finish();
        });
    }
}
