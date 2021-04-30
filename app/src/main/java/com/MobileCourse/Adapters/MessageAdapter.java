package com.MobileCourse.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Models.Discover;
import com.MobileCourse.Models.MessageDetail;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.Utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.LinkedList;

import butterknife.BindView;

public class MessageAdapter extends ListAdapter<MessageDetail,
        MessageAdapter.MessageViewHolder> {

    public MessageAdapter(@NonNull DiffUtil.ItemCallback<MessageDetail> diffCallback) {
        super(diffCallback);
    }

    @Override
    public int getItemViewType(int position) {
        MessageDetail messageDetail = getItem(position);
        boolean isSend = messageDetail.isSend();
        int sendType = isSend ? Constants.SEND:Constants.RECEIVE;
        int contentType =  Constants.TEXT;
        if(messageDetail.getContent().equals(Constants.ContentType.AUDIO)){
            contentType = Constants.AUDIO;
        } else if(messageDetail.getContent().equals(Constants.ContentType.VIDEO)){
            contentType = Constants.VIDEO;
        }
        return sendType + contentType;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int sendType = viewType & 0xf0;
        int contentType = viewType & 0x0f;
        if(sendType==Constants.SEND){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message, parent, false);
            return new MessageViewHolder(itemView, sendType,contentType);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_message, parent, false);
            return new MessageViewHolder(itemView, sendType,contentType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        // TODO
        ((MessageViewHolder)(holder)).setMessageDetail(getItem(position));
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private int sendType;
        private int contentType;

        public MessageViewHolder(@NonNull View itemView, int sendType, int contentType) {
            super(itemView);
            view = itemView;
            this.sendType = sendType;
            this.contentType = contentType;
        }

        public void setMessageDetail(MessageDetail messageDetail){
            ImageView avatarImageView = view.findViewById(R.id.avatar);
            TextView messageBodyTextView = view.findViewById(R.id.message_body);

            messageBodyTextView.setText(messageDetail.getContent());

            if(this.sendType==Constants.RECEIVE){
                TextView nameTextView = view.findViewById(R.id.name);
                nameTextView.setText(messageDetail.getUsername());
            }

            Glide.with(avatarImageView.getContext()).load(messageDetail.getAvatar()).placeholder(R.drawable.avatar2)
                    .apply(RequestOptions.circleCropTransform()).into(avatarImageView);
        }
    }

    public static class MessageDiff extends DiffUtil.ItemCallback<MessageDetail> {

        @Override
        public boolean areItemsTheSame(@NonNull MessageDetail oldItem, @NonNull MessageDetail newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MessageDetail oldItem, @NonNull MessageDetail newItem) {
            return false;
        }
    }


}

