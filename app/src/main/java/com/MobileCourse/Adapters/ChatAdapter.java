package com.MobileCourse.Adapters;

import com.MobileCourse.Models.Chat;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MobileCourse.R;
import java.util.LinkedList;

public class ChatAdapter extends BaseAdapter {

    private LinkedList<Chat> data;
    private Context context;

    public ChatAdapter(LinkedList<Chat> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.item_list_chat, parent, false);
        Chat chat = data.get(position);
        // 修改View中各个控件的属性，使之显示对应位置Chat的内容
        // 使用convertView.findViewById()方法来寻找对应的控件
        // 控件ID见 res/layout/item_list_chat.xml
        // TODO
        ImageView avatarImageView = (ImageView)convertView.findViewById(R.id.avatar_icon);
        avatarImageView.setImageResource(chat.getAvatarIcon());

        TextView nickNameTextView = (TextView)convertView.findViewById(R.id.nickname_text);
        nickNameTextView.setText(chat.getNickname());

        TextView lastSpeakTimeTextView = (TextView)convertView.findViewById(R.id.last_speak_time_text);
        lastSpeakTimeTextView.setText(chat.getLastSpeakTime());

        TextView lastSpeakTextView = (TextView)convertView.findViewById(R.id.last_speak_text);
        lastSpeakTextView.setText(chat.getLastSpeak());

        return convertView;
    }
}
