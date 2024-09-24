package com.example.androidapplecation.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidapplecation.repository.UserRepository;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.util.FormValidation;
import com.example.androidapplecation.R;

import java.util.ArrayList;
import java.util.Calendar;

public class RegisterActivity extends BaseActivity {

    private EditText editTextEmail, editTextPassword, editTextName;
    private Spinner spinnerYear, spinnerMonth, spinnerDay;
    private Button btnSubmit;
    private FormValidation formValidation;

    // 메모리에 유저 정보를 저장할 ArrayList
    private ArrayList<User> userList = new ArrayList<>();

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

        // 스피너 초기화
        spinnerYear = findViewById(R.id.spinner_year);
        spinnerMonth = findViewById(R.id.spinner_month);
        spinnerDay = findViewById(R.id.spinner_day);

        // 연도 설정 (현재 연도에서 과거 100년까지)
        ArrayList<String> years = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= currentYear - 100; i--) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                years
        );

        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        // 월 설정 (1월 ~ 12월)
        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.month_array,
                android.R.layout.simple_spinner_item);

        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(monthAdapter);

        // 일 설정 (1일부터 31일까지)
        ArrayList<String> days = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item, days
        );

        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(dayAdapter);

        // FormValidation 클래스 초기화
        formValidation = new FormValidation(
                this,
                editTextEmail,
                editTextPassword,
                editTextName,
                spinnerYear,
                spinnerMonth,
                spinnerDay
        );

        // 버튼 클릭 리스너 설정
        btnSubmit.setOnClickListener(v -> {

            if (formValidation.validateForm()) {

                // 유효성 검사가 성공했을 때 처리 로직
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String year = spinnerYear.getSelectedItem().toString();
                String month = spinnerMonth.getSelectedItem().toString();
                String day = spinnerDay.getSelectedItem().toString();

                // 새로운 User 객체 생성 및 UserRepository에 저장
                User newUser = new User(email, password, name, year, month, day);
                UserRepository.getInstance().addUser(newUser);  // 싱글톤에 저장

                // 회원가입 성공 메시지
                Toast.makeText(
                        RegisterActivity.this,
                        "회원가입 성공!",
                        Toast.LENGTH_SHORT)
                        .show();

                // 유저 정보 로그 출력 (확인용)
                for (User user : userList) {
                    System.out.println("User: " + user.getEmail() + ", " + user.getName());
                }
            }
        });
    }
}
