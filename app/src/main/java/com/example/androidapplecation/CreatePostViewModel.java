package com.example.androidapplecation;
import com.example.androidapplecation.model.Question;
import com.example.androidapplecation.repository.QuestionRepository;

import java.util.Date;

public class CreatePostViewModel {

    public boolean createNewPost(String title, String content) {
        if (title == null || content == null || title.isEmpty() || content.isEmpty()) {
            return false;
        }

        Question newQuestion = new Question(
                "id" + (QuestionRepository.getInstance().getQuestionList().size() + 1), // 새로운 ID
                title,  // 제목
                content, // 내용
                "test user",  // 작성자 (임의로 설정)
                "qid" + (QuestionRepository.getInstance().getQuestionList().size() + 1), // 질문 ID
                new Date(),  // 생성 날짜
                new Date()   // 수정 날짜
        );

        // QuestionRepository 에 새로운 질문 추가
        QuestionRepository.getInstance().addQuestion(newQuestion);

        return true;
    }
}
