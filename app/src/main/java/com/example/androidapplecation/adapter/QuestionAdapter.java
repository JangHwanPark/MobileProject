package com.example.androidapplecation.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidapplecation.R;
import com.example.androidapplecation.activity.PostDetailActivity;
import com.example.androidapplecation.model.Question;
import com.example.androidapplecation.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private List<Question> questionList;
    private final List<Question> allQuestions; // 전체 데이터를 저장하는 리스트
    private final Context context;

    public QuestionAdapter(
            List<Question> questionList,
            Context context) {
        this.questionList = new ArrayList<>(questionList); // 현재 리스트 초기화
        this.allQuestions = new ArrayList<>(questionList); // 전체 리스트 초기화
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_question_card, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        // 제목 및 내용 설정
        holder.questionTitle.setText(question.getTitle());
        holder.questionContent.setText(question.getContent());

        // 사용자 정보 설정
        holder.questionAuthor.setText(question.getAuthor().getName()); // 작성자 이름 설정
        holder.questionUserCompany.setText(question.getAuthor().getCompany()); // 회사 설정

        // 날짜 포매팅
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDate = dateFormat.format(question.getCreatedAt());
        holder.questionDate.setText(formattedDate);

        // 아이템 클릭 이벤트
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("qid", question.getId());  // question ID 전달
            intent.putExtra("title", question.getTitle()); // 제목 전달
            intent.putExtra("content", question.getContent()); // 내용 전달
            intent.putExtra("createAt", question.getCreatedAt());
            // Log.d("question", String.valueOf(intent));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    // 검색 키워드에 따라 데이터를 필터링하는 메서드
    public void filterQuestions(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            questionList = new ArrayList<>(allQuestions); // 키워드가 없으면 전체 리스트를 표시
        } else {
            List<Question> filteredList = new ArrayList<>();
            for (Question question : allQuestions) {
                // 제목이나 내용에 키워드가 포함된 경우 필터링 리스트에 추가
                if (question.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        question.getContent().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredList.add(question);
                }
            }
            questionList = filteredList;
        }
        notifyDataSetChanged(); // 화면 갱신
    }

    // 새 데이터를 받아서 화면을 업데이트하는 메서드
    public void updateQuestions(List<Question> newQuestions) {
        allQuestions.clear();
        allQuestions.addAll(newQuestions);
        questionList = new ArrayList<>(allQuestions); // 전체 데이터를 현재 리스트로 설정
        notifyDataSetChanged();
    }

    // ViewHolder 클래스
    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionTitle;
        TextView questionContent;
        TextView questionAuthor;
        TextView questionUserCompany;
        TextView questionDate;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.question_title);
            questionContent = itemView.findViewById(R.id.question_content);
            questionAuthor = itemView.findViewById(R.id.question_author);          // 사용자 이름
            questionUserCompany = itemView.findViewById(R.id.question_user_company); // 사용자 회사
            questionDate = itemView.findViewById(R.id.question_date);                // 작성 날짜
        }
    }
}
