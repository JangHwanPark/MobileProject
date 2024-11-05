package com.example.androidapplecation.ui.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplecation.R;
import com.example.androidapplecation.ui.adapter.QuestionAdapter;
import com.example.androidapplecation.ui.adapter.UserAdapter;
import com.example.androidapplecation.data.model.Question;
import com.example.androidapplecation.data.model.User;
import com.example.androidapplecation.ui.presenter.DashboardPresenter;
import com.example.androidapplecation.ui.view.DashboardView;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends BaseActivity implements DashboardView {
    private QuestionAdapter questionAdapter, freeBoardAdapter;
    private UserAdapter userAdapter;
    private RecyclerView recyclerView, mentorInfoView, questionRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // 위젯 초기화
        Button timelineTab = findViewById(R.id.timeline_tab);
        Button questionTab = findViewById(R.id.question_tab);
        Button mentorTab = findViewById(R.id.mentor_tab);

        ImageButton homeButton = findViewById(R.id.footer_home);
        ImageButton createPostButton = findViewById(R.id.post_add);
        ImageButton accountButton = findViewById(R.id.footer_account);

        // 리사이클 뷰 초기화
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mentorInfoView = findViewById(R.id.recycler_user_card);
        mentorInfoView.setLayoutManager(new LinearLayoutManager(this));

        questionRecyclerView = findViewById(R.id.recycler_question);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        timelineTab.setOnClickListener(v -> showRecyclerView());
        questionTab.setOnClickListener(v -> showQuestionView());
        mentorTab.setOnClickListener(v -> showMentorView());

        homeButton.setOnClickListener(v -> showRecyclerView());
        createPostButton.setOnClickListener(v -> startActivity(new Intent(this, CreatePostActivity.class)));
        accountButton.setOnClickListener(v -> startActivity(new Intent(this, UserAccountActivity.class)));

        // Presenter 초기화
        DashboardPresenter presenter = new DashboardPresenter(this);

        // 기본으로 timelineTab만 보이도록 설정
        showRecyclerView();

        questionAdapter = new QuestionAdapter(new ArrayList<>(), this);
        freeBoardAdapter = new QuestionAdapter(new ArrayList<>(), this);
        userAdapter = new UserAdapter(new ArrayList<>(), this);

        questionRecyclerView.setAdapter(questionAdapter);
        recyclerView.setAdapter(freeBoardAdapter);
        mentorInfoView.setAdapter(userAdapter);

        presenter.loadQuestions();
        presenter.loadFreeBoard();
        presenter.loadUsers();

        setupSearchView();
    }

    // 좋아요 버튼 기능 구현

    private void setupSearchView() {
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                questionAdapter.filterQuestions(query);
                showQuestionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                questionAdapter.filterQuestions(newText);
                showQuestionView();
                return true;
            }
        });
    }

    // DashboardView 인터페이스 메서드 구현
    @Override
    public void showQuestions(List<Question> questions) {
        questionAdapter.updateQuestions(questions);
    }

    @Override
    public void showFreeBoard(List<Question> questions) {
        freeBoardAdapter.updateQuestions(questions);
    }

    @Override
    public void showUsers(List<User> users) {
        userAdapter.updateUsers(users);
    }

    @Override
    public void showError(String message) {
        Log.e(TAG, message);
    }

    @Override
    public void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        questionRecyclerView.setVisibility(View.GONE);
        mentorInfoView.setVisibility(View.GONE);
        Log.d(TAG, "Timeline view visible");
    }

    @Override
    public void showQuestionView() {
        recyclerView.setVisibility(View.GONE);
        questionRecyclerView.setVisibility(View.VISIBLE);
        mentorInfoView.setVisibility(View.GONE);
        Log.d(TAG, "Question view visible");
    }

    @Override
    public void showMentorView() {
        recyclerView.setVisibility(View.GONE);
        questionRecyclerView.setVisibility(View.GONE);
        mentorInfoView.setVisibility(View.VISIBLE);
        Log.d(TAG, "Mentor view visible");
    }
}
