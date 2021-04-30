package com.MobileCourse.Adapters;

import com.MobileCourse.Models.Chat;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.LinkedList;

public class ChatAdapter extends ListAdapter<Chat,ChatAdapter.ChatViewHolder> {

    private LinkedList<TimeLine> data;
    private Context context;

    public ChatAdapter(@NonNull DiffUtil.ItemCallback<Chat> diffCallback) {
        super(diffCallback);
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView nickNameTextView;
        private ImageView avatarImageView;
        private TextView lastSpeakTimeTextView;
        private TextView lastSpeakTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = (ImageView)itemView.findViewById(R.id.avatar_icon);
            nickNameTextView = (TextView)itemView.findViewById(R.id.nickname_text);
            lastSpeakTimeTextView = (TextView)itemView.findViewById(R.id.last_speak_time_text);
            lastSpeakTextView = (TextView)itemView.findViewById(R.id.last_speak_text);
        }

        public void setNickName(String nickName){
            nickNameTextView.setText(nickName);
        }
        public void setAvatar(String avatar){
            Glide.with(avatarImageView.getContext()).load(avatar).placeholder(R.drawable.avatar2)
                    .apply(RequestOptions.circleCropTransform()).into(avatarImageView);;
        }
        public void setLaskSpeakTime(String time){
            lastSpeakTimeTextView.setText(time);
        }
        public void setLastSpeakText(String text){
            lastSpeakTextView.setText(text);
        }

        @SuppressLint("UnsafeExperimentalUsageError")
        public void setUnReadCount(long unReadCount){
            BadgeDrawable badgeDrawable = BadgeDrawable.create(avatarImageView.getContext());
            badgeDrawable.setVisible(true);
            badgeDrawable.setNumber((int)unReadCount);
            BadgeUtils.attachBadgeDrawable(badgeDrawable, avatarImageView);
        }

    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_chat, parent, false);
        return new ChatViewHolder(view);
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
