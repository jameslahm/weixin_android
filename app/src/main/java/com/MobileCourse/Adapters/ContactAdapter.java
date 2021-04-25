package com.MobileCourse.Adapters;

import com.MobileCourse.Models.Contact;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.R;

import java.util.LinkedList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private LinkedList<Contact> data;

    // 完成类ContactViewHolder
    // 使用itemView.findViewById()方法来寻找对应的控件
    // TODO
    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView nickNameTextView;
        private ImageView avatarImageView;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nickNameTextView = (TextView)itemView.findViewById(R.id.nickname_text);
            avatarImageView = (ImageView)itemView.findViewById(R.id.avatar_icon);
        }

        public void setNickName(String nickName){
            nickNameTextView.setText(nickName);
        }
        public void setAvatar(@DrawableRes int avatarId){
            avatarImageView.setImageResource(avatarId);
        }

    }

    public ContactAdapter(LinkedList<Contact> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_contact,parent,false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        // TODO
        Contact contact = data.get(position);
        holder.setNickName(contact.getNickname());
        holder.setAvatar(contact.getAvatarIcon());
    }

    @Override
    public int getItemCount() {
        // TODO
        return data.size();
    }
}
