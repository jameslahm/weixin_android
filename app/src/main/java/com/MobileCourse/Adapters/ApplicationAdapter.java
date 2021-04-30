package com.MobileCourse.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Models.Application;
import com.MobileCourse.Models.Chat;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.R;
import com.MobileCourse.Utils.MiscUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

import java.util.LinkedList;

public class ApplicationAdapter extends ListAdapter<Application,ApplicationAdapter.ChatViewHolder> {

    private LinkedList<TimeLine> data;
    private Context context;
    private OnConfirmCallback confirmCallbackObj;

    public interface OnConfirmCallback {
        void onConfirm(Application application);
    }

    public ApplicationAdapter(@NonNull DiffUtil.ItemCallback<Application> diffCallback,OnConfirmCallback confirmCallbackObj) {
        super(diffCallback);
        this.confirmCallbackObj = confirmCallbackObj;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView nickNameTextView;
        private ImageView avatarImageView;
        private TextView lastSpeakTimeTextView;
        private TextView lastSpeakTextView;
        private Button confirmButton;

        private OnConfirmCallback confirmCallbackObj;

        public ChatViewHolder(@NonNull View itemView,OnConfirmCallback confirmCallbackObj) {
            super(itemView);
            avatarImageView = (ImageView)itemView.findViewById(R.id.avatar_icon);
            nickNameTextView = (TextView)itemView.findViewById(R.id.nickname_text);
            lastSpeakTimeTextView = (TextView)itemView.findViewById(R.id.last_speak_time_text);
            lastSpeakTextView = (TextView)itemView.findViewById(R.id.last_speak_text);
            confirmButton = (Button)itemView.findViewById(R.id.confirmButton);
            this.confirmCallbackObj = confirmCallbackObj;
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
        public void setOnConfirm(Application application){
            confirmButton.setOnClickListener((view)->{
                confirmCallbackObj.onConfirm(application);
            });
        }
    }

    @NonNull
    @Override
    public ApplicationAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_chat, parent, false);
        return new ChatViewHolder(view,this.confirmCallbackObj);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        // TODO
        Application application = getItem(position);
        holder.setAvatar(application.getAvatar());
        holder.setLaskSpeakTime(MiscUtil.formatTimestamp(application.getTimestamp()));
        holder.setLastSpeakText(application.getContent());
        holder.setNickName(application.getUsername());
        holder.setOnConfirm(application);
    }

    public static class ApplicationDiff extends DiffUtil.ItemCallback<Application> {

        @Override
        public boolean areItemsTheSame(@NonNull Application oldItem, @NonNull Application newItem) {
            return oldItem == newItem;
        }

        // TODO: FIXME
        @Override
        public boolean areContentsTheSame(@NonNull Application oldItem, @NonNull Application newItem) {
            return false;
        }
    }

}