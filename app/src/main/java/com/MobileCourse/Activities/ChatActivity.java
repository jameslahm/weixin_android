package com.MobileCourse.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Adapters.ContactAdapter;
import com.MobileCourse.Adapters.MessageAdapter;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.Utils.MiscUtil;
import com.MobileCourse.ViewModels.MeViewModel;
import com.MobileCourse.ViewModels.MessageViewModel;
import com.MobileCourse.ViewModels.TimeLineViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    public static final String CHAT_TIMELINE_ID ="CHAT_TIMELINE_ID";

    @BindView(R.id.title)
    TextView titleTextView;

    @BindView(R.id.messages_view)
    RecyclerView messagesListView;

    @BindView(R.id.edit)
    EditText editText;


    @BindView(R.id.sendButton)
    ImageView sendButton;

    MessageViewModel messageViewModel;
    TimeLineViewModel timeLineViewModel;
    MeViewModel meViewModel;

    TimeLine timeLine;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String timeLineId = intent.getStringExtra(CHAT_TIMELINE_ID);


        MessageAdapter messageAdapter = new MessageAdapter(new MessageAdapter.MessageDiff());
        messagesListView.setAdapter(messageAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        messagesListView.setLayoutManager(linearLayoutManager);

        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        timeLineViewModel = new ViewModelProvider(this).get(TimeLineViewModel.class);
        meViewModel = new ViewModelProvider(this).get(MeViewModel.class);

        messageViewModel.getMessageDetailsLiveData().observe(this,(messageDetails)->{
            messageAdapter.submitList(messageDetails);
        });

        messageViewModel.getMessageDetails(timeLineId);

        timeLineViewModel.updateLastCheckTimestamp(timeLineId,MiscUtil.getCurrentTimestamp());

        timeLineViewModel.getTimeLineById(timeLineId).observe(this,(timeLine)->{
            this.timeLine =timeLine;
            titleTextView.setText(timeLine.getName());
        });

        sendButton.setOnClickListener((view)->{
            String text = editText.getText().toString();
            if(text.isEmpty()){
                return;
            } else {
                User me = meViewModel.getMe().getValue();
                messageViewModel.sendMessage(me,this.timeLine,text);
            }
        });

    }



}
