package com.example.androidapplecation;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplecation.activity.LoginActivity;
import com.example.androidapplecation.activity.RegisterActivity;
import com.example.androidapplecation.model.HelloResponse;
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.network.ApiService;
import com.example.androidapplecation.network.RetrofitClient;
import com.example.androidapplecation.repository.UserRepository;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 모든 사용자를 가져와서 출력
        ArrayList<User> users = UserRepository.getInstance().getUserList();
        for (User user : users) {
            Log.d(TAG, "User Email: " + user.getEmail() + ", Name: " + user.getName());
        }

        Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        Button registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "Button clicked!");
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        // 서버의 /hello 엔드포인트를 호출하는 코드
        Button callHelloButton = findViewById(R.id.call_hello);
        callHelloButton.setOnClickListener(view -> {
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<HelloResponse> call = apiService.sayHello();

            call.enqueue(new Callback<HelloResponse>() {
                @Override
                public void onResponse(Call<HelloResponse> call, Response<HelloResponse> response) {
                    if (response.isSuccessful()) {
                        // 서버로부터 받은 응답을 로그로 출력
                        Log.d("MainActivity", "Response: " + response.body());
                    } else {
                        Log.e("MainActivity", "Request failed with code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<HelloResponse> call, Throwable t) {
                    // 네트워크 오류 처리
                    Log.e("MainActivity", "Network error: " + t.getMessage());
                }
            });
        });
    }
}
