package com.example.androidapplecation.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

public class FormValidation {

    private EditText editTextEmail, editTextPassword, editTextName, editTextBirth;
    private Context context;

    // 생성자: Context와 필요한 View들을 전달받음
    public FormValidation(
            Context context,
            EditText editTextEmail,
            EditText editTextPassword,
            EditText editTextName,
            EditText editTextBirth
    ) {

        this.context = context;
        this.editTextEmail = editTextEmail;
        this.editTextPassword = editTextPassword;
        this.editTextName = editTextName;
        this.editTextBirth = editTextBirth;
    }

    // 폼 유효성 검사 함수
    public boolean validateForm() {
        // 이메일 유효성 검사
        String email = editTextEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("이메일을 입력하세요");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("올바른 이메일 형식이 아닙니다");
            return false;
        }

        // 비밀번호 유효성 검사
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("비밀번호를 입력하세요");
            return false;
        } else if (password.length() < 6) {
            editTextPassword.setError("비밀번호는 최소 6자 이상이어야 합니다");
            return false;
        }

        // 이름 유효성 검사
        String name = editTextName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            editTextName.setError("이름을 입력하세요");
            return false;
        }

        // 생년월일 유효성 검사
        String birth = editTextBirth.getText().toString().trim();
        if (TextUtils.isEmpty(birth)) {
            editTextBirth.setError("생년월일을 입력하세요.");
            return false;
        }

        // 모든 검사 통과
        return true;
    }
}
