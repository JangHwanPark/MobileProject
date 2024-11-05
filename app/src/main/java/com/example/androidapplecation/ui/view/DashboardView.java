package com.example.androidapplecation.ui.view;

import com.example.androidapplecation.data.model.Question;
import com.example.androidapplecation.data.model.User;

import java.util.List;

public interface DashboardView {
    void showQuestions(List<Question> questions);
    void showFreeBoard(List<Question> questions);
    void showUsers(List<User> users);
    void showError(String message);
    void showRecyclerView();
    void showQuestionView();
    void showMentorView();
}
