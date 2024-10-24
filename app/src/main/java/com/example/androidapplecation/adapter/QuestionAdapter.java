package com.example.androidapplecation.adapter;

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

    private final ArrayList<Question> questionList;
    private OnItemClickListener onItemClickListener;

    // 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(Question question);
    }

    // 생성자
    public QuestionAdapter(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    // 클릭 리스너 설정 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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
        holder.dateTextView.setText(question.getCreatedAt() != null
                ? question.getCreatedAt().toString()
                : "날짜 없음");

        // 아이템 클릭 이벤트 설정
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(question); // 클릭된 질문 객체 전달
            }
        });
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
