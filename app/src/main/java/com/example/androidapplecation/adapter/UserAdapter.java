package com.example.androidapplecation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplecation.R;
import com.example.androidapplecation.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> userList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 필드 정의
        public ImageView userImage;
        public TextView userName;
        public TextView userCompany;
        public ImageButton followButton;

        public ViewHolder(View view) {
            super(view);
            userImage = view.findViewById(R.id.user_image);
            userName = view.findViewById(R.id.user_name);
            userCompany = view.findViewById(R.id.user_company);
            followButton = view.findViewById(R.id.follow_button);
        }
    }

    // 생성자
    public UserAdapter(List<User> users) {
        this.userList = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_user_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.getName());
        holder.userCompany.setText("소속회사 없음"); // user.getCompany()
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
