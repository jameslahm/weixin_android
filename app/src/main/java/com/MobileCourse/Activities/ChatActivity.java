package com.MobileCourse.Activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView titleTextView;

    @BindView(R.id.messages_view)
    RecyclerView messagesListView;

    @BindView(R.id.edit)
    EditText editText;


    @BindView(R.id.sendButton)
    ImageView sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);


    }



}
