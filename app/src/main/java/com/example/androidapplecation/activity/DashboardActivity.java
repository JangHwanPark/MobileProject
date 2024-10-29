package com.example.androidapplecation.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplecation.adapter.QuestionAdapter;
import com.example.androidapplecation.model.Question;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends BaseActivity {
    private QuestionAdapter questionAdapter, freeBoardAdapter, userAdapter;
    private RecyclerView recyclerView, mentorInfoView, questionRecyclerView;
    private Button timelineTab, questionTab, mentorTab;
    private ImageButton homeButton, createPostButton, accountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeViews();

        // 어댑터 초기화 시 Context 전달
        questionAdapter = new QuestionAdapter(new ArrayList<>(), this);
        freeBoardAdapter = new QuestionAdapter(new ArrayList<>(), this);
        userAdapter = new QuestionAdapter(new ArrayList<>(), this);

        questionRecyclerView.setAdapter(questionAdapter);
        recyclerView.setAdapter(freeBoardAdapter);

        loadQuestions();
        loadFreeBoard();
        loadSelectUser();
    }

    private void loadQuestions() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<List<Question>> callQuestionData = apiService.getCategoryQuestion();
        callQuestionData.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(@NonNull Call<List<Question>> call, @NonNull Response<List<Question>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Question> questions = response.body();
                    questionAdapter.updateQuestions(questions);
                    Log.d(TAG, "질문 데이터를 성공적으로 가져왔습니다.");
                } else {
                    Log.e(TAG, "질문 데이터를 가져오지 못했습니다.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Question>> call, @NonNull Throwable t) {
                Log.e(TAG, "서버 통신 오류: " + t.getMessage());
            }
        });
    }

    private void loadFreeBoard() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<List<Question>> callFreeBoardData = apiService.getCategoryFreeBoard();
        callFreeBoardData.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(@NonNull Call<List<Question>> call, @NonNull Response<List<Question>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Question> questions = response.body();
                    freeBoardAdapter.updateQuestions(questions);
                    Log.d(TAG, "자유게시판 데이터를 성공적으로 가져왔습니다.");
                } else {
                    Log.e(TAG, "자유게시판 데이터를 가져오지 못했습니다. 응답 코드: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Question>> call, @NonNull Throwable t) {
                Log.e(TAG, "서버 통신 오류: " + t.getMessage());
            }
        });
    }

    private void loadSelectUser() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<List<User>> callUserData = apiService.getUserList();
        callUserData.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    freeBoardAdapter.updateQuestions(users);
                    Log.d(TAG, "자유게시판 데이터를 성공적으로 가져왔습니다.");
                } else {
                    Log.e(TAG, "자유게시판 데이터를 가져오지 못했습니다. 응답 코드: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mentorInfoView = findViewById(R.id.recycler_user_card);
        mentorInfoView.setLayoutManager(new LinearLayoutManager(this));

        questionRecyclerView = findViewById(R.id.recycler_question);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        timelineTab = findViewById(R.id.timeline_tab);
        questionTab = findViewById(R.id.question_tab);
        mentorTab = findViewById(R.id.mentor_tab);
        homeButton = findViewById(R.id.footer_home);
        createPostButton = findViewById(R.id.post_add);
        accountButton = findViewById(R.id.footer_account);

        setupTabListeners();
        setupFooterButtonListeners();
    }

    private void setupTabListeners() {
        timelineTab.setOnClickListener(v -> showRecyclerView());
        questionTab.setOnClickListener(v -> showQuestionView());
        mentorTab.setOnClickListener(v -> showMentorView());
    }

    private void setupFooterButtonListeners() {
        homeButton.setOnClickListener(v -> showRecyclerView());
        createPostButton.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, CreatePostActivity.class)));
        accountButton.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, UserAccountActivity.class)));
    }

    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        questionRecyclerView.setVisibility(View.GONE);
        mentorInfoView.setVisibility(View.GONE);
    }

    private void showQuestionView() {
        recyclerView.setVisibility(View.GONE);
        questionRecyclerView.setVisibility(View.VISIBLE);
        mentorInfoView.setVisibility(View.GONE);
    }

    private void showMentorView() {
        recyclerView.setVisibility(View.GONE);
        questionRecyclerView.setVisibility(View.GONE);
        mentorInfoView.setVisibility(View.VISIBLE);
    }
}
