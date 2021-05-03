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
import com.MobileCourse.Utils.Constants;
import com.MobileCourse.Utils.MiscUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

import java.util.LinkedList;

public class ApplicationAdapter extends ListAdapter<Application,ApplicationAdapter.ApplicationViewHolder> {

    private LinkedList<TimeLine> data;
    private OnConfirmCallback confirmCallbackObj;
    private Context context;

    public interface OnConfirmCallback {
        void onConfirm(Application application);
    }

    public ApplicationAdapter(@NonNull DiffUtil.ItemCallback<Application> diffCallback,OnConfirmCallback confirmCallbackObj,Context context) {
        super(diffCallback);
        this.context = context;
        this.confirmCallbackObj = confirmCallbackObj;
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder {
        private TextView nickNameTextView;
        private ImageView avatarImageView;
        private TextView applyReasonTextView;
        private Button confirmButton;

        private OnConfirmCallback confirmCallbackObj;

        public ApplicationViewHolder(@NonNull View itemView,OnConfirmCallback confirmCallbackObj) {
            super(itemView);
            avatarImageView = (ImageView)itemView.findViewById(R.id.avatar_icon);
            nickNameTextView = (TextView)itemView.findViewById(R.id.nickname_text);
            applyReasonTextView = (TextView)itemView.findViewById(R.id.apply_reason);
            confirmButton = (Button)itemView.findViewById(R.id.confirmButton);
            this.confirmCallbackObj = confirmCallbackObj;
        }

        public void setNickName(String nickName){
            nickNameTextView.setText(nickName);
        }
        public void setAvatar(String avatar){
            MiscUtil.loadImage(avatarImageView,avatar);
        }
        public void setApplyReason(String text){
            applyReasonTextView.setText(text);
        }
        public void setOnConfirm(Application application){
            confirmButton.setOnClickListener((view)->{
                confirmCallbackObj.onConfirm(application);
            });
        }
    }

    @NonNull
    @Override
    public ApplicationAdapter.ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_application, parent, false);
        return new ApplicationViewHolder(view,this.confirmCallbackObj);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        // TODO
        Application application = getItem(position);
        holder.setAvatar(application.getAvatar());
        holder.setApplyReason(application.getContent());
        holder.setNickName(application.getUsername());
        holder.setOnConfirm(application);
    }

    public static class ApplicationDiff extends DiffUtil.ItemCallback<Application> {

        @Override
        public boolean areItemsTheSame(@NonNull Application oldItem, @NonNull Application newItem) {
            return oldItem.getFrom().equals(newItem.getFrom());
        }

        // TODO: FIXME
        @Override
        public boolean areContentsTheSame(@NonNull Application oldItem, @NonNull Application newItem) {
            return oldItem.getTimestamp()==newItem.getTimestamp();
        }
    }

}