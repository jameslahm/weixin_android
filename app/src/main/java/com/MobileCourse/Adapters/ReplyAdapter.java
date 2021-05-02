package com.MobileCourse.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Models.Reply;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;

public class ReplyAdapter extends ListAdapter<Reply, ReplyAdapter.ReplyViewHolder> {
    public ReplyAdapter(@NonNull DiffUtil.ItemCallback<Reply> diffCallback) {
        super(diffCallback);
    }

    public static class ReplyViewHolder extends RecyclerView.ViewHolder {
        private TextView nickNameTextView;
        private TextView replyTextView;

        @SuppressLint("UnsafeExperimentalUsageError")
        public ReplyViewHolder(@NonNull View itemView) {
            super(itemView);
            nickNameTextView = (TextView)itemView.findViewById(R.id.name);
            replyTextView = (TextView)itemView.findViewById(R.id.reply);
        }

        public void setReply(Reply reply){
            nickNameTextView.setText(reply.getUser().getUsername());
            replyTextView.setText(reply.getContent());
        }
    }

    @NonNull
    @Override
    public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_reply, parent, false);
        return new ReplyViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull ReplyViewHolder holder, int position) {
        // TODO
        Reply reply = getItem(position);
        holder.setReply(reply);
    }

    public static class ReplyDiff extends DiffUtil.ItemCallback<Reply> {

        @Override
        public boolean areItemsTheSame(@NonNull Reply oldItem, @NonNull Reply newItem) {
            return oldItem == newItem;
        }

        // TODO: FIXME
        @Override
        public boolean areContentsTheSame(@NonNull Reply oldItem, @NonNull Reply newItem) {
            return false;
        }
    }
}
