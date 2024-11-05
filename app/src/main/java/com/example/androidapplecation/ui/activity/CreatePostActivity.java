// CreatePostActivity.java
package com.example.androidapplecation.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.androidapplecation.R;
import com.example.androidapplecation.data.model.Question;
import com.example.androidapplecation.ui.presenter.CreatePostPresenter;
import com.example.androidapplecation.ui.view.CreatePostView;

import java.util.Date;

public class CreatePostActivity extends BaseActivity implements CreatePostView {

    private EditText titleEditText;
    private EditText contentEditText;
    private Spinner categorySpinner;
    private CreatePostPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // Presenter 초기화
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        presenter = new CreatePostPresenter(this, sharedPreferences);

        // UI 요소 초기화
        titleEditText = findViewById(R.id.question_title);
        contentEditText = findViewById(R.id.question_content);
        categorySpinner = findViewById(R.id.category_spinner);
        Spinner interestSpinner = findViewById(R.id.interest_spinner);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                this, R.array.post_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> interestAdapter = ArrayAdapter.createFromResource(
                this, R.array.post_interest, android.R.layout.simple_spinner_item);
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestSpinner.setAdapter(interestAdapter);

        Button submitButton = findViewById(R.id.header_btn_check);
        submitButton.setVisibility(View.VISIBLE);
        submitButton.setOnClickListener(v -> handleClickSubmitButton());
    }

    private void handleClickSubmitButton() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();

        if (category.equals("질문답변") || category.equals("자유 게시판")) {
            Question newQuestion = new Question(null, -1, title, content, category, new Date(), new Date());
            presenter.sendQuestionToServer(newQuestion);  // Presenter를 통해 서버에 전송
        }
        finish();
    }

    @Override
    public void showLoginRequiredMessage() {
        Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
