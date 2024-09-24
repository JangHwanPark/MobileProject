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
import com.example.androidapplecation.model.User;
import com.example.androidapplecation.repository.UserRepository;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_account);

        // 모든 사용자를 가져와서 출력
        ArrayList<User> users = UserRepository.getInstance().getUserList();
        for (User user : users) {
            Log.d(TAG, "User Email: " + user.getEmail() + ", Name: " + user.getName());
        }

        /*Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });*/

        /*Button registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "Button clicked!");
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });*/
    }
}