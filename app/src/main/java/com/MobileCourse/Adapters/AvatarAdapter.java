package com.MobileCourse.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import android.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Models.Chat;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.Utils.MiscUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AvatarAdapter extends ArrayAdapter<User> {
    public AvatarAdapter(@NonNull Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_avatar, parent, false);
        }
        User user = getItem(position);
        ImageView avatarImageView = listitemView.findViewById(R.id.avatar_icon);
        TextView nickNameTextView = listitemView.findViewById(R.id.nickname_text);
        nickNameTextView.setText(user.getUsername());
        MiscUtil.loadImage(avatarImageView,user.getAvatar());
        return listitemView;
    }
}