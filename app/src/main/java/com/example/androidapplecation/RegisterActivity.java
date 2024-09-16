package com.example.androidapplecation;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class RegisterActivity extends BaseActivity {

    private EditText editTextEmail, editTextPassword, editTextName;
    private Spinner spinnerYear, spinnerMonth, spinnerDay;
    private Button btnSubmit;
    private FormValidation formValidation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 버튼 클릭 시 뒤로 가기
        setupUndoButton();

        // 뷰 초기화
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        editTextName = findViewById(R.id.editText_name);
        spinnerYear = findViewById(R.id.spinner_year);
        spinnerMonth = findViewById(R.id.spinner_month);
        spinnerDay = findViewById(R.id.spinner_day);
        btnSubmit = findViewById(R.id.btn_submit);

        // FormValidation 클래스 초기화
        formValidation = new FormValidation(this, editTextEmail, editTextPassword, editTextName,
                spinnerYear, spinnerMonth, spinnerDay);

        // 버튼 클릭 리스너 설정
        btnSubmit.setOnClickListener(v -> {
            if (formValidation.validateForm()) {
                // 유효성 검사가 성공했을 때 처리 로직
                Toast.makeText(
                        RegisterActivity.this,
                        "회원가입 성공!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
