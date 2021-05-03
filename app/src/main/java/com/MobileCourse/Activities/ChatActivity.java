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
import com.MobileCourse.Fragments.SendActionFragment;
import com.MobileCourse.Models.Group;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.Utils.Constants;
import com.MobileCourse.Utils.MiscUtil;
import com.MobileCourse.ViewModels.MeViewModel;
import com.MobileCourse.ViewModels.MessageViewModel;
import com.MobileCourse.ViewModels.TimeLineViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatActivity extends AppCompatActivity {

    public static final String CHAT_TIMELINE_ID ="CHAT_TIMELINE_ID";

    public static final String CREATE_TIMELINE = "CREATE_TIMELINE";

    public static final String CREATE_TIMELINE_USER = "CREATE_TIMELINE_USER";
    public static final String CREATE_TIMELINE_GROUP = "CREATE_TIMELINE_GROUP";

    @BindView(R.id.title)
    TextView titleTextView;

    @BindView(R.id.messages_view)
    RecyclerView messagesListView;

    @BindView(R.id.edit)
    EditText editText;


    @BindView(R.id.sendButton)
    ImageView sendButton;

    @BindView(R.id.detail)
    ImageView detailImageView;

    MessageViewModel messageViewModel;
    TimeLineViewModel timeLineViewModel;
    MeViewModel meViewModel;

    TimeLine timeLine;

    @BindView(R.id.action)
    ImageView actionImageView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String timeLineId = intent.getStringExtra(CHAT_TIMELINE_ID);

        String createTimeLineBy = intent.getStringExtra(CREATE_TIMELINE);


        MessageAdapter messageAdapter = new MessageAdapter(new MessageAdapter.MessageDiff());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        messagesListView.setLayoutManager(linearLayoutManager);
        messagesListView.setAdapter(messageAdapter);
        messagesListView.smoothScrollToPosition(messageAdapter.getItemCount());

        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        timeLineViewModel = new ViewModelProvider(this).get(TimeLineViewModel.class);
        meViewModel = new ViewModelProvider(this).get(MeViewModel.class);

//        timeLineViewModel.updateLastCheckTimestamp(timeLineId,MiscUtil.getCurrentTimestamp());

        messageViewModel.getMessageDetailsLiveData().observe(this,(messageDetails)->{
            messageAdapter.submitList(messageDetails);
        });

        messageViewModel.getMessageDetails(timeLineId);


        timeLineViewModel.getTimeLineById(timeLineId).observe(this,(timeLine)->{
            this.timeLine =timeLine;
            if(timeLine==null){
                // Insert TimeLine
                if(createTimeLineBy.equals("USER")){
                    User user = intent.getParcelableExtra(CREATE_TIMELINE_USER);
                    timeLineViewModel.insertTimeLineByUser(user);
                }
                if(createTimeLineBy.equals("GROUP")){
                    Group group = intent.getParcelableExtra(CREATE_TIMELINE_GROUP);
                    timeLineViewModel.insertTimeLineByGroup(group);
                }
                return;
            }

            titleTextView.setText(timeLine.getName());

            if(timeLine.getMessageType().equals(Constants.MessageType.GROUP)){
                detailImageView.setOnClickListener((view)->{
                    Intent intent1 = new Intent(getApplicationContext(),GroupProfile.class);
                    intent1.putExtra(GroupProfile.GROUP_ID_KEY,timeLine.getId());
                    startActivity(intent1);
                });
            } else {
                detailImageView.setOnClickListener((view)->{
                    Intent intent1 = new Intent(getApplicationContext(),ProfileActivity.class);
                    intent1.putExtra(ProfileActivity.USER_ID_KEY,timeLine.getId());
                    startActivity(intent1);
                });
            }
        });

        sendButton.setOnClickListener((view)->{
            String text = editText.getText().toString();
            if(text.isEmpty()){
                return;
            } else {
                User me = meViewModel.getMe().getValue();
                messageViewModel.sendMessage(me,this.timeLine,text,Constants.ContentType.TEXT);
            }
        });

        actionImageView.setOnClickListener((view)->{
            SendActionFragment.display(((content, contentType) -> {
                User me = meViewModel.getMe().getValue();
                messageViewModel.sendMessage(me,this.timeLine,content,contentType);
            }),getSupportFragmentManager());
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        timeLineViewModel.updateLastCheckTimestamp(this.timeLine.getId(), MiscUtil.getCurrentTimestamp());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
