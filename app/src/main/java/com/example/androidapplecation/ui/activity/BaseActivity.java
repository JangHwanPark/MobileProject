package com.example.androidapplecation.ui.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.androidapplecation.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.component_header);
     }

    protected void setupUndoButton() {
        View headerView = findViewById(R.id.component_header); // 인클루드된 뷰 전체를 찾기
        Button undoButton = headerView.findViewById(R.id.buttonId_undo); // 인클루드된 뷰 안의 버튼 찾기
        undoButton.setOnClickListener(v -> {
            Log.d("DetailsActivity", "뒤로 가기 버튼 클릭됨");
            finish();
        });
    }
}