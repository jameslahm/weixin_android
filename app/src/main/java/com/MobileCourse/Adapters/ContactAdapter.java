package com.MobileCourse.Adapters;

import com.MobileCourse.Models.Contact;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.LinkedList;

public class ContactAdapter extends ListAdapter<User, ContactAdapter.ContactViewHolder> {

    private OnClickCallback onClickCallbackobj;
    private boolean enableCheckBox;

    public ContactAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback, OnClickCallback onClickCallbackObj, boolean enableCheckBox) {
        super(diffCallback);
        this.onClickCallbackobj =onClickCallbackObj;
        this.enableCheckBox = enableCheckBox;
    }

    public interface OnClickCallback {
        void onClick(User user, View view);
    }

//    private LinkedList<Contact> data;

    // 完成类ContactViewHolder
    // 使用itemView.findViewById()方法来寻找对应的控件
    // TODO
    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView nickNameTextView;
        private ImageView avatarImageView;
        private View view;
        private boolean enableCheckBox;
        public ContactViewHolder(@NonNull View itemView, boolean enableCheckBox) {
            super(itemView);
            view = itemView;
            this.enableCheckBox = enableCheckBox;
            nickNameTextView = (TextView)itemView.findViewById(R.id.nickname_text);
            avatarImageView = (ImageView)itemView.findViewById(R.id.avatar_icon);
        }

        public void setNickName(String nickName){
            nickNameTextView.setText(nickName);
        }
        public void setAvatar(String avatar){
            Glide.with(avatarImageView.getContext()).load(avatar).placeholder(R.drawable.avatar2)
                    .apply(RequestOptions.circleCropTransform()).into(avatarImageView);;
        }
        public void setOnClick(User user,OnClickCallback onClickCallbackObj){
            if(enableCheckBox){
                itemView.findViewById(R.id.checkbox).setOnClickListener((view1)->{
                    onClickCallbackObj.onClick(user,view.findViewById(R.id.checkbox));
                });
            } else {
                view.setOnClickListener((view)->{
                    onClickCallbackObj.onClick(user,null);
                });
            }
        }
    }


    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO
        View itemView;
        if(enableCheckBox){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_new_group,parent,false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_contact,parent,false);
        }
        return new ContactViewHolder(itemView,enableCheckBox);
    }


    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        // TODO
        User user = getItem(position);
        holder.setNickName(user.getUsername());
        holder.setAvatar(user.getAvatar());
        holder.setOnClick(user,onClickCallbackobj);
    }


    public static class ContactDiff extends DiffUtil.ItemCallback<User> {

        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    }
}
