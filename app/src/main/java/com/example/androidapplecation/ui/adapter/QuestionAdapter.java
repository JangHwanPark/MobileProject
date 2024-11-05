package com.example.androidapplecation.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidapplecation.R;
import com.example.androidapplecation.ui.activity.PostDetailActivity;
import com.example.androidapplecation.data.model.Question;
import com.example.androidapplecation.ui.presenter.LikePresenter;
import com.example.androidapplecation.ui.view.LikeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * {@code QuestionAdapter}는 {@link Question} 항목 목록을 표시하는 RecyclerView 어댑터입니다.
 * 키워드로 질문을 필터링하고, 질문 목록을 업데이트하는 기능을 제공합니다.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> implements LikeView {
    private List<Question> questionList;
    private final List<Question> allQuestions; // 전체 데이터를 저장하는 리스트
    private final Context context;
    private final LikePresenter likePresenter;

    /**
     * {@code QuestionAdapter}를 생성합니다.
     *
     * @param questionList 초기 질문 목록.
     * @param context      어댑터가 사용되는 컨텍스트.
     */
    public QuestionAdapter(
            List<Question> questionList,
            Context context) {
        this.questionList = new ArrayList<>(questionList); // 현재 리스트 초기화
        this.allQuestions = new ArrayList<>(questionList); // 전체 리스트 초기화
        this.context = context;
        this.likePresenter = new LikePresenter(this); // LikePresenter를 생성할 때 LikeView로 QuestionAdapter를 전달
    }

    /**
     * 질문 항목 뷰를 생성하고 새로운 {@link QuestionViewHolder}를 반환합니다.
     *
     * @param parent   부모 ViewGroup.
     * @param viewType 새로운 뷰의 뷰 타입.
     * @return 새로운 {@link QuestionViewHolder} 인스턴스.
     */
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_question_card, parent, false);
        return new QuestionViewHolder(view);
    }

    /**
     * 지정된 위치에 있는 {@link QuestionViewHolder}에 데이터를 바인딩합니다.
     * 항목 클릭 및 좋아요 버튼 클릭 리스너를 설정합니다.
     *
     * @param holder   데이터를 바인딩할 ViewHolder.
     * @param position 데이터 집합에서의 항목 위치.
     */
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

        // 좋아요 설정
        holder.questionLikeCount.setText("좋아요 " + question.getGreat() + "개");
        holder.questionCardLikeButton.setOnClickListener(v -> {
            likePresenter.likeQuestion(question.getId()); // 좋아요 API 호출
        });

        // 아이템 클릭 이벤트
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("qid", question.getId());  // question ID 전달
            intent.putExtra("authorName", question.getAuthor().getName()); // 작성자 전달
            intent.putExtra("authorCompany", question.getAuthor().getCompany()); // 작성자 회사 전달
            intent.putExtra("title", question.getTitle()); // 제목 전달
            intent.putExtra("content", question.getContent()); // 내용 전달
            intent.putExtra("createAt", question.getCreatedAt());
            context.startActivity(intent);
        });
    }

    /**
     * 어댑터가 보유한 데이터 집합의 전체 항목 수를 반환합니다.
     *
     * @return 전체 항목 수.
     */
    @Override
    public int getItemCount() {
        return questionList.size();
    }

    /**
     * 키워드에 따라 질문 목록을 필터링하고, 표시되는 목록을 업데이트합니다.
     *
     * @param keyword 검색 키워드.
     */
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

    /**
     * 새로운 질문 목록으로 어댑터를 업데이트하고, 화면을 갱신합니다.
     *
     * @param newQuestions 새로 표시할 질문 목록.
     */
    public void updateQuestions(List<Question> newQuestions) {
        allQuestions.clear();
        allQuestions.addAll(newQuestions);
        questionList = new ArrayList<>(allQuestions); // 전체 데이터를 현재 리스트로 설정
        notifyDataSetChanged();
    }

    /**
     * 질문 항목을 위한 ViewHolder 클래스입니다. 각 질문 항목에 대한 뷰를 참조합니다.
     */
    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionTitle;
        TextView questionContent;
        TextView questionAuthor;
        TextView questionUserCompany;
        TextView questionDate;
        TextView questionLikeCount;
        Button questionCardLikeButton;

        /**
         * 새로운 {@code QuestionViewHolder}를 생성하고 뷰 참조를 초기화합니다.
         *
         * @param itemView 질문 항목의 루트 뷰.
         */
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.question_title);
            questionContent = itemView.findViewById(R.id.question_content);
            questionAuthor = itemView.findViewById(R.id.question_author);          // 사용자 이름
            questionUserCompany = itemView.findViewById(R.id.question_user_company); // 사용자 회사
            questionDate = itemView.findViewById(R.id.question_date);                // 작성 날짜
            questionLikeCount = itemView.findViewById(R.id.questionLikeCount);
            questionCardLikeButton = itemView.findViewById(R.id.questionCardLikeButton);
        }
    }

    @Override
    public void onLikeSuccess(int newLikeCount) {
        Toast.makeText(context, "좋아요 성공! 새로운 좋아요 수: " + newLikeCount, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLikeFailure(String errorMessage) {
        Toast.makeText(context, "좋아요 실패: " + errorMessage, Toast.LENGTH_SHORT).show();
    }
}
