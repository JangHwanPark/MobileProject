package com.example.androidapplecation.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplecation.R;
import com.example.androidapplecation.adapter.CommentAdapter;
import com.example.androidapplecation.model.Comment;
import com.example.androidapplecation.presenter.PostDetailPresenter;
import com.example.androidapplecation.view.PostDetailView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDetailActivity extends BaseActivity implements PostDetailView {
    private EditText editTextComment;
    private RecyclerView recyclerViewComments;
    private TextView textViewNoComments;
    private CommentAdapter commentAdapter;
    private final List<Comment> commentList = new ArrayList<>();
    private PostDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // SharedPreferences 초기화
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);

        // Presenter 초기화
        presenter = new PostDetailPresenter(this, sharedPreferences);

        // UI 요소 초기화
        initializeViews();

        // 댓글 목록 불러오기
        presenter.loadComments();
    }

    private void initializeViews() {
        // 버튼 클릭 시 뒤로 가기
        setupUndoButton();

        // UI 요소와 연결
        // 게시글 작성자 정보
        TextView detailUserName = findViewById(R.id.detailQuestionAuthor);
        TextView detailUserCompany = findViewById(R.id.detailQuestionCompany);
        
        // 게시글 정보
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewContent = findViewById(R.id.textViewContent);
        TextView textViewCreateAt = findViewById(R.id.textViewCreateAt);

        // 리사이클뷰 초기화
        recyclerViewComments = findViewById(R.id.recyclerViewComments);
        textViewNoComments = findViewById(R.id.textViewNoComments);

        // RecyclerView와 어댑터 초기화
        commentAdapter = new CommentAdapter(commentList, this::showCommentPopupMenu);
        recyclerViewComments.setAdapter(commentAdapter);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));

        // 댓글 입력 필드와 전송 버튼 연결
        editTextComment = findViewById(R.id.editTextComment);
        Button buttonSendComment = findViewById(R.id.buttonSendComment);

        // Intent로부터 데이터 받기
        String authorName = getIntent().getStringExtra("authorName");
        String authorCompany = getIntent().getStringExtra("authorCompany");
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        String createAt = getIntent().getStringExtra("createAt");

        detailUserName.setText(authorName != null ? authorName : "이름없음");
        detailUserCompany.setText(authorCompany != null ? authorCompany : "회사없음");
        textViewTitle.setText(title != null ? title : "No Title");
        textViewContent.setText(content != null ? content : "No Content");
        textViewCreateAt.setText(createAt != null ? createAt : "No Date");

        // 버튼 클릭 이벤트
        buttonSendComment.setOnClickListener(v -> handleCommentSubmit());

        // 햄버거 아이콘 버튼 초기화
        ImageButton buttonMoreOptions = findViewById(R.id.button_more_options);

        // 햄버거 버튼 클릭 시 팝업 메뉴 표시
        buttonMoreOptions.setOnClickListener(this::showPopupMenu);
    }

    // 댓글 등록
    private void handleCommentSubmit() {
        String commentText = editTextComment.getText().toString().trim();
        if (!commentText.isEmpty()) {
            Comment comment = new Comment();
            comment.setContent(commentText);
            comment.setQid(getQid());
            comment.setCreateAt(new Date());
            presenter.postComment(comment); // 프레젠터에서 댓글 등록 처리
        } else {
            Toast.makeText(this, "댓글을 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    // 팝업 메뉴
    @Override
    public void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.post_options_menu, popup.getMenu());

        // 메뉴 항목 클릭 리스너 설정
        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.actionEditPost) {
                editPost();
                return true;
            } else if (itemId == R.id.actionDeletePost) {
                presenter.deletePost(getQid());
                return true;
            }
            return false;
        });
        popup.show();
    }

    // 게시글 수정
    private void editPost() {
        Toast.makeText(this, "게시글 수정하기", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommentPopupMenu(View view, int commentId) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.comment_options_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.commentActionEditPost) {
                editComment(commentId);  // 댓글 수정
                return true;
            } else if (itemId == R.id.commentActionDeletePost) {
                presenter.deleteComment(commentId);  // 댓글 삭제
                return true;
            }
            return false;
        });
        popup.show();
    }

    // 댓글 수정
    private void editComment(int commentId) {
        Toast.makeText(this, "댓글 수정하기", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComments(List<Comment> comments) {
        commentList.clear();
        commentList.addAll(comments);
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNoCommentsMessage() {
        textViewNoComments.setVisibility(View.VISIBLE);
        recyclerViewComments.setVisibility(View.GONE);
    }

    @Override
    public void hideNoCommentsMessage() {
        textViewNoComments.setVisibility(View.GONE);
        recyclerViewComments.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCommentPosted() {
        Toast.makeText(this, "Comment posted successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommentUpdated() {
        Toast.makeText(this, "Comment updated successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommentDeleted() {
        Toast.makeText(this, "Comment deleted successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getQid() {
        return getIntent().getIntExtra("qid", -1);
    }
}