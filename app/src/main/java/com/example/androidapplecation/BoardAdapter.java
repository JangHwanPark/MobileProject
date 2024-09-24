package com.example.androidapplecation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplecation.model.Board;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {

    private ArrayList<Board> boardList;

    public BoardAdapter(ArrayList<Board> boardList) {
        this.boardList = boardList;
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_post_card, parent, false); // 게시물 카드 레이아웃을 사용
        return new BoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        Board board = boardList.get(position);

        // 게시물 데이터를 뷰에 설정
        holder.userName.setText(board.getName());
        holder.userCompany.setText("소속회사 없음");
        holder.postDate.setText(board.getDate().toString());
        holder.postContent.setText(board.getArticle());
        holder.userImage.setImageResource(R.drawable.ic_launcher_foreground); // 기본 이미지 설정
    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }

    public static class BoardViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, userCompany, postDate, postContent;
        public ImageView userImage;
        public ImageButton followButton;

        public BoardViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userCompany = itemView.findViewById(R.id.user_company);
            postDate = itemView.findViewById(R.id.post_date);
            postContent = itemView.findViewById(R.id.post_content);
            userImage = itemView.findViewById(R.id.user_image);
            followButton = itemView.findViewById(R.id.follow_button);
        }
    }
}
