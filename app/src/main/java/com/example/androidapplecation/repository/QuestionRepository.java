package com.example.androidapplecation.repository;
import android.util.Log;

import com.example.androidapplecation.model.Board;
import com.example.androidapplecation.model.Question;

import java.util.ArrayList;
import java.util.Date;

public class QuestionRepository {
    private static QuestionRepository instance;
    private ArrayList<Question> questionsList;

    private QuestionRepository() {
        questionsList = new ArrayList<>();
        initializeQuestion();
    }

    public static QuestionRepository getInstance() {
        if (instance == null) {
            instance = new QuestionRepository();
        }
        return instance;
    }

    private void initializeQuestion() {
        for (int i = 1; i <= 11; i++) {
            Question question = new Question(
                    "id" + i,
                    "title" + i,
                    "content" + i,
                    "test user" + i,
                    "qid" + i,
                    new Date(),
                    new Date()
            );
            questionsList.add(question);
        }
    }

    public ArrayList<Question> getQuestionList() {
        return questionsList;
    }

    // 메모리 내 데이터를 저장하는 로직
    public void addQuestion(Question question) {
        questionsList.add(question);
    }

    // 전체 게시글을 로그로 출력하는 메서드
    public void logAllQuestion() {
        Log.d("BoardRepository", "전체 게시글 목록 출력:");
        for (Question question : questionsList) {
            Log.d("BoardRepository", "작성자: " + question.getAuthor() + ", 제목: " + question.getTitle() + ", 내용: " + question.getContent() + ", 작성일: " + question.getCreatedAt().toString());
        }
    }
}
