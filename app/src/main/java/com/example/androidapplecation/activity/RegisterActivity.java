package com.example.androidapplecation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Arrays;
import java.util.Calendar;

public class RegisterActivity extends BaseActivity {

    private EditText editTextEmail, editTextPassword, editTextName;
    private Spinner spinnerYear, spinnerMonth, spinnerDay;
    private Button btnSubmit;
    private FormValidation formValidation;

    private static final String TAG = "RegisterActivity";  // 로그 태그

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 뒤로가기 버튼 설정
        setupUndoButton();

        // 뷰 초기화
        initViews();

        // 날짜 스피너 설정
        setupSpinners();

        // FormValidation 초기화
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
                registerUser();
            }
        });
    }

    // 뷰 초기화 메서드
    private void initViews() {
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        editTextName = findViewById(R.id.editText_name);
        spinnerYear = findViewById(R.id.spinner_year);
        spinnerMonth = findViewById(R.id.spinner_month);
        spinnerDay = findViewById(R.id.spinner_day);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    // 스피너 설정 메서드
    private void setupSpinners() {
        // 연도 설정 (선택하세요 추가)
        ArrayList<String> years = new ArrayList<>();
        years.add("연도 선택");
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
        String[] monthsArray = getResources().getStringArray(R.array.month_array);
        ArrayList<String> monthsList = new ArrayList<>(Arrays.asList(monthsArray));
        monthsList.add(0, "월 선택");

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                monthsList
        );
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(monthAdapter);

        // 일 설정은 기본 1~31일, 월에 따라 동적으로 설정
        updateDaysSpinner(31);  // 초기 값은 31일

        // 연도 또는 월을 변경할 때 일 수 업데이트
        spinnerYear.setOnItemSelectedListener(new OnDateChangedListener());
        spinnerMonth.setOnItemSelectedListener(new OnDateChangedListener());
    }

    // 일 수 스피너를 업데이트하는 메서드
    private void updateDaysSpinner(int maxDay) {
        ArrayList<String> days = new ArrayList<>();
        days.add("일 선택");
        for (int i = 1; i <= maxDay; i++) {
            days.add(Integer.toString(i));
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                days
        );
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(dayAdapter);
    }

    // 유저 등록 처리 메서드
    private void registerUser() {
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
                Toast.LENGTH_SHORT
        ).show();

        // 로그 출력
        Log.d(TAG, "User registered: " + newUser.getEmail() + ", " + newUser.getName());

        Intent intent = new Intent(
                RegisterActivity.this,
                LoginActivity.class
        );
        startActivity(intent);
    }

    // 날짜 변경에 따른 리스너 클래스
    private class OnDateChangedListener implements android.widget.AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
            String selectedYear = spinnerYear.getSelectedItem().toString();
            String selectedMonth = spinnerMonth.getSelectedItem().toString();

            if (!selectedYear.equals("연도 선택") && !selectedMonth.equals("월 선택")) {
                int year = Integer.parseInt(selectedYear);
                int month = Integer.parseInt(selectedMonth.replace("월", "").trim());

                // 월에 따른 최대 일수 계산 (윤년 고려)
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month - 1, 1);  // month는 0부터 시작하므로 -1
                int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                // 일 스피너 업데이트
                updateDaysSpinner(maxDay);
            }
        }

        @Override
        public void onNothingSelected(android.widget.AdapterView<?> parent) {
            // 아무것도 선택되지 않았을 때 처리할 내용 없음
        }
    }
}
