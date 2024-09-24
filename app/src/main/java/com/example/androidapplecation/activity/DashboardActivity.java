package com.example.androidapplecation.activity;

import android.os.Bundle;
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
    private BoardAdapter boardAdapter;
    private ArrayList<Board> boardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.recycler_view); // XML에서 설정한 RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // BoardRepository에서 게시글 목록 가져오기
        boardList = BoardRepository.getInstance().getBoardList();

        // 어댑터 설정
        boardAdapter = new BoardAdapter(boardList);
        recyclerView.setAdapter(boardAdapter);

        // 데이터를 UI에 매핑하는 메서드 호출
        populateData();
    }

    // 데이터를 각 UI 요소에 매핑하는 메서드
    private void populateData() {
        // 기타 UI 요소들 설정 가능 (생략)
    }
}
