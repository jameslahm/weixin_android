package com.MobileCourse.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Models.Chat;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

public class LikeAdapter extends ListAdapter<User,LikeAdapter.LikeViewHolder> {

    public LikeAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback) {
        super(diffCallback);
    }

    public static class LikeViewHolder extends RecyclerView.ViewHolder {
        private TextView nickNameTextView;

        @SuppressLint("UnsafeExperimentalUsageError")
        public LikeViewHolder(@NonNull View itemView) {
            super(itemView);
            nickNameTextView = (TextView)itemView.findViewById(R.id.name);
        }

        public void setUser(User user){
            nickNameTextView.setText(user.getUsername());
        }
    }

    @NonNull
    @Override
    public LikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_like, parent, false);
        return new LikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeViewHolder holder, int position) {
        // TODO
        User user = getItem(position);
        holder.setUser(user);
    }

    public static class LikeDiff extends DiffUtil.ItemCallback<User> {

        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        // TODO: FIXME
        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return true;
        }
    }
}
