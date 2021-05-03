package com.MobileCourse.Adapters;

import com.MobileCourse.Models.Contact;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.MobileCourse.Utils.MiscUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.LinkedList;

// TODO Refactor Me
public class ContactAdapter extends ListAdapter<User, ContactAdapter.ContactViewHolder> {

    private OnClickCallback onClickCallbackobj;
    private boolean enableCheckBox;
    private OnDeleteCallback onDeleteCallbackObj;

    public ContactAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback, OnClickCallback onClickCallbackObj, OnDeleteCallback onDeleteCallbackObj, boolean enableCheckBox) {
        super(diffCallback);
        this.onClickCallbackobj =onClickCallbackObj;
        this.enableCheckBox = enableCheckBox;
        this.onDeleteCallbackObj = onDeleteCallbackObj;
    }

    public interface OnClickCallback {
        void onClick(User user, View view);
    }

    public interface OnDeleteCallback {
        void onDelete(User user);
    }

//    private LinkedList<Contact> data;

    // 完成类ContactViewHolder
    // 使用itemView.findViewById()方法来寻找对应的控件
    // TODO
    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView nickNameTextView;
        private ImageView avatarImageView;
        private Button deleteButton;
        private View view;
        private boolean enableCheckBox;
        public ContactViewHolder(@NonNull View itemView, boolean enableCheckBox) {
            super(itemView);
            view = itemView;
            this.enableCheckBox = enableCheckBox;
            nickNameTextView = (TextView)itemView.findViewById(R.id.nickname_text);
            avatarImageView = (ImageView)itemView.findViewById(R.id.avatar_icon);
            deleteButton = (Button)itemView.findViewById(R.id.deleteButton);
        }

        public void setNickName(String nickName){
            nickNameTextView.setText(nickName);
        }
        public void setAvatar(String avatar){
            MiscUtil.loadImage(avatarImageView,avatar);
        }
        public void setOnClick(User user,OnClickCallback onClickCallbackObj, OnDeleteCallback onDeleteCallbackObj){
            if(enableCheckBox){
                itemView.findViewById(R.id.checkbox).setOnClickListener((view1)->{
                    onClickCallbackObj.onClick(user,view.findViewById(R.id.checkbox));
                });
            } else {
                view.setOnClickListener((view)->{
                    onClickCallbackObj.onClick(user,null);
                });
            }
            if(deleteButton!=null){
                deleteButton.setOnClickListener((view)->{
                    onDeleteCallbackObj.onDelete(user);
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
        holder.setOnClick(user,onClickCallbackobj,onDeleteCallbackObj);
    }


    public static class ContactDiff extends DiffUtil.ItemCallback<User> {

        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getUsername().equals(newItem.getUsername());
        }
    }
}
