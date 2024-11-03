package com.example.androidapplecation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidapplecation.R;
import com.example.androidapplecation.model.Comment;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        // Comment 정보 표시
        Comment comment = commentList.get(position);
        holder.textViewCommentContent.setText(comment.getContent());
        holder.textViewCommentCreateDate.setText(comment.getCreateAt().toString());

        // User 정보 표시
        if (comment.getUser() != null) {
            holder.textViewUserName.setText(comment.getUser().getName());
            holder.textViewUserCompany.setText(comment.getUser().getCompany());
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCommentContent;
        TextView textViewCommentCreateDate; // 댓글 작성 시간
        TextView textViewUserName;          // 사용자 이름 표시
        TextView textViewUserCompany;       // 사용자 회사 정보 표시

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCommentContent = itemView.findViewById(R.id.textViewCommentContent);
            textViewUserName = itemView.findViewById(R.id.textViewCommentUserName);
            textViewUserCompany = itemView.findViewById(R.id.textViewCommentUserCompany);
            textViewCommentCreateDate = itemView.findViewById(R.id.textViewCommentCreateDate);
        }
    }

    public void updateComments(List<Comment> newComments) {
        commentList = newComments;
        notifyDataSetChanged();
    }
}
