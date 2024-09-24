package com.example.androidapplecation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplecation.adapter.BoardAdapter;
import com.example.androidapplecation.adapter.UserAdapter;

import com.example.androidapplecation.model.User;
import com.example.androidapplecation.repository.BoardRepository;
import com.example.androidapplecation.R;

import com.example.androidapplecation.model.Board;
import com.example.androidapplecation.repository.UserRepository;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView, mentorInfoView;
    private TextView  questionTextView;
    private Button timelineTab, questionTab, mentorTab;
    private ImageButton homeButton, createPostButton, accountButton;

    private BoardAdapter boardAdapter;
    private UserAdapter userAdapter;

    private ArrayList<Board> boardList;
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TextView 초기화
        questionTextView = findViewById(R.id.question_text);

        mentorInfoView = findViewById(R.id.recycler_user_card);
        mentorInfoView.setLayoutManager(new LinearLayoutManager(this));

        // BoardRepository에서 게시글 목록 가져오기
        boardList = BoardRepository.getInstance().getBoardList();

        // UserRepository에서 사용자 목록 가져오기
        userList = UserRepository.getInstance().getUserList();

        // 어댑터 설정
        boardAdapter = new BoardAdapter(boardList);
        recyclerView.setAdapter(boardAdapter);

        userAdapter = new UserAdapter(userList);
        mentorInfoView.setAdapter(userAdapter);

        // 탭 버튼 초기화
        timelineTab = findViewById(R.id.timeline_tab);
        questionTab = findViewById(R.id.question_tab);
        mentorTab = findViewById(R.id.mentor_tab);

        // 기본적으로 타임라인(RecyclerView)를 출력
        showRecyclerView();

        // 타임라인 탭 클릭 시 RecyclerView 표시
        timelineTab.setOnClickListener(v -> showRecyclerView());

        // 질문답변 탭 클릭 시 TextView 표시
        questionTab.setOnClickListener(v -> showQuestionView());

        // 멘토찾기 탭 클릭 시 TextView 표시
        mentorTab.setOnClickListener(v -> showMentorView());

        // Footer View
        homeButton = findViewById(R.id.footer_home);
        homeButton.setOnClickListener(v -> showRecyclerView());

        // Footer View - 게시글 작성 버튼
        createPostButton = findViewById(R.id.post_add);
        createPostButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    DashboardActivity.this,
                    CreatePostActivity.class);
            startActivity(intent);
        });

        // Footer View - 사용자 정보 버튼
        accountButton = findViewById(R.id.footer_account);
        accountButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    DashboardActivity.this,
                    UserAccountActivity.class
            );
            startActivity(intent);
        });
    }

    // RecyclerView를 보여주는 메서드
    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        questionTextView.setVisibility(View.GONE);
        mentorInfoView.setVisibility(View.GONE);
    }

    // 질문답변 TextView를 보여주는 메서드
    private void showQuestionView() {
        recyclerView.setVisibility(View.GONE);
        questionTextView.setVisibility(View.VISIBLE);
        mentorInfoView.setVisibility(View.GONE);
    }

    // 멘토찾기 TextView를 보여주는 메서드
    private void showMentorView() {
        recyclerView.setVisibility(View.GONE);
        questionTextView.setVisibility(View.GONE);
        mentorInfoView.setVisibility(View.VISIBLE);
    }
}
