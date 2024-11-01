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
        Comment comment = commentList.get(position);
        holder.textViewCommentContent.setText(comment.getContent());
        // 필요에 따라 추가 필드 설정 가능
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCommentContent;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCommentContent = itemView.findViewById(R.id.textViewCommentContent);
        }
    }

    public void updateComments(List<Comment> newComments) {
        commentList = newComments;
        notifyDataSetChanged();
    }
}
