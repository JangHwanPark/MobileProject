package com.example.androidapplecation.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidapplecation.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_header);
     }

    protected void setupUndoButton() {
        Button undoButton = findViewById(R.id.buttonId_undo);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 뒤로 가기 버튼 클릭 처리
                finish();
            }
        });
    }
}