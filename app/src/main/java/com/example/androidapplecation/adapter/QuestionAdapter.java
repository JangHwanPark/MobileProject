package com.example.androidapplecation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplecation.R;
import com.example.androidapplecation.model.Question;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private ArrayList<Question> questionList;

    // Constructor
    public QuestionAdapter(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // XML 레이아웃을 뷰로 변환하여 ViewHolder에 전달
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_question_card, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);

        holder.titleTextView.setText(question.getTitle() != null
                ? question.getTitle()
                : "No Title");
        holder.contentTextView.setText(question.getContent() != null
                ? question.getContent()
                : "No Content");
        holder.authorTextView.setText(question.getAuthor() != null
                ? question.getAuthor()
                : "작성자 없음");
        holder.dateTextView.setText(question.getCreatedAt() != null
                ? question.getCreatedAt().toString()
                : "날짜 없음");
    }

    @Override
    public int getItemCount() {
        // 아이템의 개수 반환
        return questionList.size();
    }

    // ViewHolder 클래스 (아이템 뷰를 가리키는 역할)
    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, contentTextView, authorTextView, dateTextView;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.question_title);  // XML과 동일한 ID인지 확인
            contentTextView = itemView.findViewById(R.id.question_content);  // XML과 동일한 ID인지 확인
            authorTextView = itemView.findViewById(R.id.question_author);  // XML과 동일한 ID인지 확인
            dateTextView = itemView.findViewById(R.id.question_date);  // XML과 동일한 ID인지 확인
        }
    }

}
