package com.example.androidapplecation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class ComponentHeader extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_header);
    }

    @Override
    public void onBackPressed() {
        // 뒤로 가기 로직 구현
        Toast.makeText(this, "뒤로 가기 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();

        // 예를 들어, 조건에 따라 액티비티를 종료하거나, 백 스택으로 돌아가게 할 수 있습니다.
        super.onBackPressed();  // 기본 뒤로가기 동작을 실행하려면 이 줄을 호출
    }
}