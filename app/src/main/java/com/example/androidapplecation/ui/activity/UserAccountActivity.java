package com.example.androidapplecation.ui.activity;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplecation.R;
import com.example.androidapplecation.data.model.Question;
import com.example.androidapplecation.data.model.User;
import com.example.androidapplecation.ui.adapter.QuestionAdapter;
import com.example.androidapplecation.ui.presenter.UserAccountPresenter;
import com.example.androidapplecation.ui.view.UserAccountView;

import java.util.ArrayList;
import java.util.List;

public class UserAccountActivity extends BaseActivity implements UserAccountView {
    private TextView userName, userCompany;
    private QuestionAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        // 뒤로 가기 버튼 초기화
        setupUndoButton();

        // Presenter 초기화
        UserAccountPresenter presenter = new UserAccountPresenter(this, this);

        // 사용자 정보 요청
        presenter.fetchUserInfo();
        presenter.fetchUserPostList();

        // TextView 초기화
        userName = findViewById(R.id.userAccountInfoName);
        userCompany = findViewById(R.id.userAccountInfoCompany);
        Button buttonEditProfile = findViewById(R.id.buttonEditProfile);
        Button buttonLogout = findViewById(R.id.buttonLogout);

        // RecyclerView 초기화
        RecyclerView userPostsRecyclerView = findViewById(R.id.recyclerViewMyPosts); // XML에 정의된 RecyclerView ID 사용
        userPostsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        questionAdapter = new QuestionAdapter(new ArrayList<>(), this); // 빈 ArrayList로 초기화
        userPostsRecyclerView.setAdapter(questionAdapter);

        // 이벤트 리스너
        buttonEditProfile.setOnClickListener(v -> {
            Toast.makeText(this, "프로필 편집 클릭", Toast.LENGTH_SHORT).show();
        });

        buttonLogout.setOnClickListener(v -> {
            Toast.makeText(this, "로그아웃 클릭", Toast.LENGTH_SHORT).show();
        });
    }

    // 내가 작성한 게시글 출력
    @Override
    public void showUserPosts(List<Question> questionsList) {
        // 어댑터에 새로운 질문 목록 설정
        questionAdapter.updateQuestions(questionsList);

        // 게시글이 없을 때 메시지 표시
        if (questionsList.isEmpty()) {
            Toast.makeText(this, "작성한 게시글이 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // UserAccountView 인터페이스 구현
    @Override
    public void showUserInfo(User user) {
        Log.d(TAG, "사용자 어카운트 정보 : " + user.toString());

        // uid 저장
        int userId = user.getUid();  // userId 변수에 저장
        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("uid", userId);  // SharedPreferences에 저장
        editor.apply();

        userName.setText(user.getName());
        userCompany.setText(user.getCompany());
        Toast.makeText(this, "Hello, " + user.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getToken() {
        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        return prefs.getString("token", null);
    }
}