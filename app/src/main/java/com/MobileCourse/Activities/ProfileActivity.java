package com.MobileCourse.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;

import com.MobileCourse.Api.Resource;
import com.MobileCourse.Fragments.EditDialogFragment;
import com.MobileCourse.Models.Friend;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.Utils.Constants;
import com.MobileCourse.Utils.MiscUtil;
import com.MobileCourse.ViewModels.MeViewModel;
import com.MobileCourse.ViewModels.ProfileViewModel;
import com.MobileCourse.ViewModels.TimeLineViewModel;
import com.MobileCourse.WebSocket.MessageService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity {

    public static final String USER_WEIXIN_ID_KEY = "USER_WEIXIN_ID_KEY";
    public static final String USER_ID_KEY = "USER_ID_KEY";

    @BindView(R.id.user_nickname)
    TextView nickNameTextView;

    @BindView(R.id.user_avatar)
    ImageView avatarImageView;

    @BindView(R.id.user_weixinId)
    TextView weixinIdTextView;

    @BindView(R.id.user_action)
    ViewGroup actionViewGroup;

    @BindView(R.id.user_action_text)
    TextView actionTextView;

    @BindView(R.id.syncTimeLine)
    ViewGroup syncTimeLine;

    @BindView(R.id.deleteTimeLine)
    ViewGroup deleteTimeLineMessage;

    ProfileViewModel profileViewModel;

    TimeLineViewModel timelineViewModel;

    MeViewModel meViewModel;

    boolean isFriend;

    User user;

    String applyAddFriendText = "";

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String weixinId =  intent.getStringExtra(USER_WEIXIN_ID_KEY);
        String userId = intent.getStringExtra(USER_ID_KEY);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        meViewModel = new ViewModelProvider(this).get(MeViewModel.class);
        timelineViewModel = new ViewModelProvider(this).get(TimeLineViewModel.class);

        if(weixinId!=null && !weixinId.isEmpty()) {
            profileViewModel.getProfileByWeixinId(weixinId).observe(this, (resource) -> {
                if (resource != null) {
                    switch (resource.status) {
                        case LOADING: {
                            break;
                        }
                        case ERROR: {
                            Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case SUCCESS: {
                            User user = resource.data;
                            this.user = user;
                            nickNameTextView.setText(user.getUsername());
                            MiscUtil.loadImage(avatarImageView, user.getAvatar());
                            weixinIdTextView.setText(user.getWeixinId());

                            meViewModel.getMe().observe(this, (me) -> {
                                List<Friend> friends = me.getFriends();
                                boolean isFriend = friends.stream().anyMatch((friend) -> {
                                    return friend.getId().equals(user.getId());
                                });
                                this.isFriend = isFriend;
                                if (isFriend) {
                                    actionTextView.setText(Constants.ACTION_SEND_MESSAGE);
                                } else {
                                    actionTextView.setText(Constants.ACTION_ADD_FRIEND);
                                }
                            });

                            break;
                        }
                    }
                }
            });
        }
        if(userId!=null && !userId.isEmpty()){
            profileViewModel.getProfileById(userId).observe(this, (user) -> {
                if (user != null) {
                    this.user = user;
                    nickNameTextView.setText(user.getUsername());
                    MiscUtil.loadImage(avatarImageView, user.getAvatar());
                    weixinIdTextView.setText(user.getWeixinId());

                    meViewModel.getMe().observe(this, (me) -> {
                        List<Friend> friends = me.getFriends();
                        boolean isFriend = friends.stream().anyMatch((friend) -> {
                            return friend.getId().equals(user.getId());
                        });
                        this.isFriend = isFriend;
                        if (isFriend) {
                            actionTextView.setText(Constants.ACTION_SEND_MESSAGE);
                        } else {
                            actionTextView.setText(Constants.ACTION_ADD_FRIEND);
                        }
                    });
                }
            });
        }



        actionViewGroup.setOnClickListener((view)->{
            FragmentManager fragmentManager = getSupportFragmentManager();
            if(!isFriend){
                EditDialogFragment.display("添加好友",applyAddFriendText,(text)->{
                    this.applyAddFriendText = text;
                    meViewModel.applyAddFriend(user.getId(),this.applyAddFriendText);
                },fragmentManager);
            } else {
                Intent intent1 = new Intent(getApplicationContext(),ChatActivity.class);
                intent1.putExtra(ChatActivity.CHAT_TIMELINE_ID,user.getId());
                intent1.putExtra(ChatActivity.CREATE_TIMELINE_USER,user);
                intent1.putExtra(ChatActivity.CREATE_TIMELINE,"USER");
                startActivity(intent1);
            }
        });

        syncTimeLine.setOnClickListener((view)->{
            timelineViewModel.syncTimeLineInSingle(user).observe(this,(resource)->{
                if(resource!=null){
                    switch (resource.status){
                        case SUCCESS:{
                            Toast.makeText(getApplicationContext(), "同步成功", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case ERROR:{
                            break;
                        }
                    }
                }
            });
        });

        deleteTimeLineMessage.setOnClickListener((view)->{
            timelineViewModel.deleteTimeLineMessages(user.getId());
        });

//        meViewModel.getMe().observe(this,(user)->{
//            // For Update
//        });
    }




}
