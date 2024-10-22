package com.example.androidapplecation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplecation.adapter.QuestionAdapter;
import com.example.androidapplecation.adapter.UserAdapter;
import com.example.androidapplecation.model.Question;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.repository.BoardRepository;
import com.example.androidapplecation.R;
import com.example.androidapplecation.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends BaseActivity {
    private static final String TAG = "DashboardActivity";  // 로그 태그
    private RecyclerView recyclerView, mentorInfoView, questionRecyclerView;
    private Button timelineTab, questionTab, mentorTab;
    private ImageButton homeButton, createPostButton, accountButton;
    private QuestionAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // SearchView 초기화
        SearchView searchView = findViewById(R.id.search_view);

        // SearchView 리스너 설정
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 사용자가 검색어를 제출했을 때
                Log.d(TAG, "검색어 제출: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 검색어 입력이 변경될 때
                Log.d(TAG, "검색어 입력 중: " + newText);
                return false;
            }
        });

        // 초기화 메서드 호출
        initializeViews();
        initializeAdapters();
        initializeFooterButtons();

        // 서버에서 데이터 호출
        loadQuestions();
        loadFreeBoard();

        // 탭 버튼 리스너 설정
        setupTabListeners();

        // 하단 네비게이션 바의 버튼 리스너 설정
        setupFooterButtonListeners();

        // 로그
        BoardRepository.getInstance().logAllBoards();
    }

    private void loadQuestions() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // 서버에서 질문 데이터를 가져옴
        Call<List<Question>> callQuestionData = apiService.getCategoryQuestion();
        callQuestionData.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<Question>> call,
                    @NonNull Response<List<Question>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Question> questions = response.body();
                    ArrayList<Question> questionArrayList = new ArrayList<>(questions);

                    // 질문 데이터를 어댑터에 설정
                    questionAdapter = new QuestionAdapter(questionArrayList);
                    questionRecyclerView.setAdapter(questionAdapter);
                    questionAdapter.notifyDataSetChanged();
                    Log.d(TAG, "질문 데이터를 성공적으로 가져왔습니다.");
                } else {
                    Log.e(TAG, "질문 데이터를 가져오지 못했습니다.");
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Log.e(TAG, "서버 통신 오류: " + t.getMessage());
            }
        });
    }

    private void loadFreeBoard() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // 서버에서 자유게시판 데이터를 가져옴
        Call<List<Question>> callFreeBoardData = apiService.getCategoryFreeBoard();
        callFreeBoardData.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<Question>> call,
                    @NonNull Response<List<Question>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Question> questions = response.body();
                    ArrayList<Question> freeBoardList = new ArrayList<>(questions);

                    questionAdapter = new QuestionAdapter(freeBoardList);
                    recyclerView.setAdapter(questionAdapter);
                    questionAdapter.notifyDataSetChanged();
                    Log.d(TAG, "자유게시판 데이터를 성공적으로 가져왔습니다.");
                } else {
                    Log.e(TAG, "자유게시판 데이터를 가져오지 못했습니다. 응답 코드: " + response.code());
                }
            }


            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Log.e(TAG, "서버 통신 오류: " + t.getMessage());
            }
        });
    }

    // 뷰 초기화 메서드
    private void initializeViews() {
        // init Recycler View
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mentorInfoView = findViewById(R.id.recycler_user_card);
        mentorInfoView.setLayoutManager(new LinearLayoutManager(this));

        questionRecyclerView = findViewById(R.id.recycler_question);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // init components
        timelineTab = findViewById(R.id.timeline_tab);
        questionTab = findViewById(R.id.question_tab);
        mentorTab = findViewById(R.id.mentor_tab);
        homeButton = findViewById(R.id.footer_home);
        createPostButton = findViewById(R.id.post_add);
        accountButton = findViewById(R.id.footer_account);
    }

    // 어댑터 초기화 메서드
    private void initializeAdapters() {
        // UserRepository에서 사용자 목록 가져오기 및 어댑터 설정
        UserAdapter userAdapter = new UserAdapter(UserRepository.getInstance().getUserList());
        mentorInfoView.setAdapter(userAdapter);
        questionRecyclerView.setAdapter(questionAdapter);
    }

    // 하단 버튼(글 작성, 사용자 정보 등) 초기화
    private void initializeFooterButtons() {
        createPostButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    DashboardActivity.this,
                    CreatePostActivity.class);
            startActivity(intent);
        });

        accountButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    DashboardActivity.this,
                    UserAccountActivity.class);
            startActivity(intent);
        });
    }

    // 탭 버튼 리스너 설정
    private void setupTabListeners() {
        timelineTab.setOnClickListener(v -> showRecyclerView());
        questionTab.setOnClickListener(v -> showQuestionView());
        mentorTab.setOnClickListener(v -> showMentorView());
    }

    // 하단 네비게이션 바 버튼 리스너 설정
    private void setupFooterButtonListeners() {
        homeButton.setOnClickListener(v -> showRecyclerView());
    }

    // RecyclerView를 보여주는 메서드
    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        questionRecyclerView.setVisibility(View.GONE);
        mentorInfoView.setVisibility(View.GONE);
    }

    // 질문답변 TextView를 보여주는 메서드
    private void showQuestionView() {
        recyclerView.setVisibility(View.GONE);
        questionRecyclerView.setVisibility(View.VISIBLE);
        mentorInfoView.setVisibility(View.GONE);
    }

    // 멘토찾기 RecyclerView를 보여주는 메서드
    private void showMentorView() {
        recyclerView.setVisibility(View.GONE);
        questionRecyclerView.setVisibility(View.GONE);
        mentorInfoView.setVisibility(View.VISIBLE);
    }
}
