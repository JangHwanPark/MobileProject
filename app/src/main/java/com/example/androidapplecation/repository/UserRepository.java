package com.example.androidapplecation.repository;

import com.example.androidapplecation.model.User;

import java.util.ArrayList;

public class UserRepository {

    private static UserRepository instance;
    private ArrayList<User> userList = new ArrayList<>();

    private UserRepository() {
        userList = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            // 기본 테스트 계정 추가
            User testUser = new User(
                    "" + i,
                    "1",
                    "Test User" + i + 1,
                    "19900101"
            );
            userList.add(testUser);
        }
    }

    // 싱글톤 인스턴스 반환
    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }

        return instance;
    }

    // 유저 추가 메서드
    public void addUser(User user) {
        userList.add(user);
    }

    // 유저 리스트 반환 메서드
    public ArrayList<User> getUserList() {
        return userList;
    }

    // 특정 이메일로 유저 검색
    public User findUserByEmail(String email) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null; // 유저를 찾지 못하면 null 반환
    }
}
