package com.MobileCourse.Adapters;

import com.MobileCourse.Models.Chat;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.Utils.MiscUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.LinkedList;

public class ChatAdapter extends ListAdapter<Chat,ChatAdapter.ChatViewHolder> {
    private OnClickCallback onClickCallbackObj;
    Context context;

    public ChatAdapter(@NonNull DiffUtil.ItemCallback<Chat> diffCallback,OnClickCallback onClickCallbackObj, Context context) {
        super(diffCallback);
        this.onClickCallbackObj = onClickCallbackObj;
        this.context = context;
    }

    public interface OnClickCallback {
        void onClick(Chat chat);
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView nickNameTextView;
        private ImageView avatarImageView;
        private TextView lastSpeakTimeTextView;
        private TextView lastSpeakTextView;
        private BadgeDrawable badgeDrawable;
        private FrameLayout frameLayout;
        View view;
        Context context;

        @SuppressLint("UnsafeExperimentalUsageError")
        public ChatViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            view = itemView;
            this.context = context;
            avatarImageView = (ImageView)itemView.findViewById(R.id.avatar_icon);
            nickNameTextView = (TextView)itemView.findViewById(R.id.nickname_text);
            lastSpeakTimeTextView = (TextView)itemView.findViewById(R.id.last_speak_time_text);
            lastSpeakTextView = (TextView)itemView.findViewById(R.id.last_speak_text);
            frameLayout = itemView.findViewById(R.id.frame);

            badgeDrawable = BadgeDrawable.create(frameLayout.getContext());
        }

        public void setNickName(String nickName){
            nickNameTextView.setText(nickName);
        }
        public void setAvatar(String avatar){
            MiscUtil.loadImage(avatarImageView,avatar);
        }
        public void setLaskSpeakTime(String time){
            lastSpeakTimeTextView.setText(time);
        }
        public void setLastSpeakText(String text){
            lastSpeakTextView.setText(text);
        }

        @SuppressLint("UnsafeExperimentalUsageError")
        public void setUnReadCount(long unReadCount){
            if(unReadCount>0){
                badgeDrawable.setVisible(true);
                badgeDrawable.setNumber((int)unReadCount);
                frameLayout.setForeground(badgeDrawable);
                frameLayout.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
                    BadgeUtils.attachBadgeDrawable(badgeDrawable, avatarImageView, frameLayout);
                });
            } else {
                badgeDrawable.setVisible(false);
            }
        }

        public void setOnClick(Chat chat,OnClickCallback onClickCallbackObj){
            view.setOnClickListener((view)->{
                onClickCallbackObj.onClick(chat);
            });
        }

    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_chat, parent, false);
        return new ChatViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        // TODO
        Chat chat = getItem(position);
        holder.setAvatar(chat.getAvatar());
        holder.setLaskSpeakTime(chat.getLastSpeakTime());
        holder.setLastSpeakText(chat.getLastSpeak());
        holder.setNickName(chat.getNickname());
        holder.setUnReadCount(chat.getUnReadCount());
        holder.setOnClick(chat,onClickCallbackObj);
    }

    public static class ChatDiff extends DiffUtil.ItemCallback<Chat> {

        @Override
        public boolean areItemsTheSame(@NonNull Chat oldItem, @NonNull Chat newItem) {
            return oldItem == newItem;
        }

        // TODO: FIXME
        @Override
        public boolean areContentsTheSame(@NonNull Chat oldItem, @NonNull Chat newItem) {
            return false;
        }
    }

}
