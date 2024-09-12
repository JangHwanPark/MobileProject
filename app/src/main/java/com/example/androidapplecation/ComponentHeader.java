package com.example.androidapplecation;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ComponentHeader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_header);
        Button undoButton = findViewById(R.id.buttonId_undo);
        System.out.println(undoButton);

        // 언두 버튼 클릭 시 뒤로 가기 구현
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 액티비티 종료 (뒤로 가기 효과)
                finish();
            }
        });
    }
}