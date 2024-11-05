package com.example.androidapplecation.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplecation.R;
import com.example.androidapplecation.data.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final List<User> userList;
    private final Context context;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.component_user_card, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.userNameTextView.setText(user.getName() != null ? user.getName() : "Unknown");
        holder.userEmailTextView.setText(user.getEmail() != null ? user.getEmail() : "No Email");
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // Update user list and notify adapter
    public void updateUsers(List<User> newUserList) {
        userList.clear();
        if (newUserList != null) {userList.addAll(newUserList);}
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTextView, userEmailTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.mentor_user_name);
            userEmailTextView = itemView.findViewById(R.id.mentor_user_company);
        }
    }
}
