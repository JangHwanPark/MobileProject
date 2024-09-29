package com.example.androidapplecation.repository;
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
        for (int i = 1; i <= 10; i++) {
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
}
