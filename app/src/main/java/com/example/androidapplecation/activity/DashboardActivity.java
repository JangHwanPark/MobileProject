package com.example.androidapplecation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidapplecation.BoardRepository;
import com.example.androidapplecation.R;
import com.example.androidapplecation.adapter.BoardAdapter;
import com.example.androidapplecation.model.Board;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView questionTextView, mentorTextView;
    private BoardAdapter boardAdapter;
    private ArrayList<Board> boardList;
    private Button timelineTab, questionTab, mentorTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TextView 초기화
        questionTextView = findViewById(R.id.question_text);
        mentorTextView = findViewById(R.id.mentor_text);

        // BoardRepository에서 게시글 목록 가져오기
        boardList = BoardRepository.getInstance().getBoardList();

        // 어댑터 설정
        boardAdapter = new BoardAdapter(boardList);
        recyclerView.setAdapter(boardAdapter);

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
    }

    // RecyclerView를 보여주는 메서드
    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        questionTextView.setVisibility(View.GONE);
        mentorTextView.setVisibility(View.GONE);
    }

    // 질문답변 TextView를 보여주는 메서드
    private void showQuestionView() {
        recyclerView.setVisibility(View.GONE);
        questionTextView.setVisibility(View.VISIBLE);
        mentorTextView.setVisibility(View.GONE);
    }

    // 멘토찾기 TextView를 보여주는 메서드
    private void showMentorView() {
        recyclerView.setVisibility(View.GONE);
        questionTextView.setVisibility(View.GONE);
        mentorTextView.setVisibility(View.VISIBLE);
    }
}
