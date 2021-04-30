package com.MobileCourse;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.MobileCourse.Adapters.FragmentAdapter;
import com.MobileCourse.Fragments.ChatFragment;
import com.MobileCourse.Fragments.ContactFragment;
import com.MobileCourse.Fragments.FindFragment;
import com.MobileCourse.Fragments.SettingsFragment;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Repositorys.TimeLineRepository;
import com.MobileCourse.Utils.Constants;
import com.MobileCourse.Utils.EventListenerUtil;
import com.MobileCourse.Utils.MiscUtil;
import com.MobileCourse.Utils.NotificationUtil;
import com.MobileCourse.Utils.WebSocket;
import com.MobileCourse.ViewModels.ApplicationViewModel;
import com.MobileCourse.ViewModels.ChatViewModel;
import com.MobileCourse.ViewModels.FriendsViewModel;
import com.MobileCourse.ViewModels.TimeLineViewModel;
import com.MobileCourse.WebSocket.MessageApi;
import com.MobileCourse.WebSocket.MessageService;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final String tag = "MainActivity";

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.navigation)
    BottomNavigationView navigationMenu;

    public static Handler msgHandler;

    private MessageApi messageApi;

    private TimeLineViewModel timeLineViewModel;
    private ApplicationViewModel applicationViewModel;
    private ChatViewModel chatViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"HandlerLeak", "CheckResult"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 自动绑定view
        ButterKnife.bind(this);

        // 以下为fragment相关设置
        FragmentManager fm = getSupportFragmentManager();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ChatFragment());
        fragments.add(new ContactFragment());
        fragments.add(new FindFragment());
        fragments.add(new SettingsFragment());

        viewPager.setAdapter(new FragmentAdapter(fm, fragments));


        navigationMenu.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_chat:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_address_book:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_find:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_about_me:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        });
        // 以上为fragment相关设置

        // 消息处理
        msgHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
            }
        };

        // 关键权限必须动态申请
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

        messageApi = MessageService.getInstance().getMessageApi();
        timeLineViewModel = new ViewModelProvider(this).get(TimeLineViewModel.class);
        applicationViewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        // handle InviteIntoGroupMessage and ApplicationMessage
        messageApi.observeApplicationMessage().subscribe((applicationMessage -> {
            if(applicationMessage.getUser()!=null && applicationMessage.getMessageType().equals(Constants.MessageType.APPLICATION)) {
                applicationViewModel.insertApplication(applicationMessage);
                NotificationUtil.sendNotification(getApplicationContext());
            }
        }));

        messageApi.observeInviteIntoGroupMessage().subscribe((inviteInToGroupMessage)->{
            if(inviteInToGroupMessage.getGroup()!=null){
                timeLineViewModel.inviteInToGroup(inviteInToGroupMessage);
                NotificationUtil.sendNotification(getApplicationContext());
            }
        });

        messageApi.observePing().subscribe((ping)->{
            if(ping.getPing()!=null) {
                NotificationUtil.sendNotification(getApplicationContext());
            }
        });

        messageApi.observeMessage().subscribe((message)->{
            if(MiscUtil.checkSingleOrGroupMessage(message)){
                timeLineViewModel.insertMessage(message);
                NotificationUtil.sendNotification(getApplicationContext());
            }
        });

        messageApi.observeConfirmMessage().subscribe((confirmMessage -> {
            Log.e(tag,String.valueOf(confirmMessage));
            String messageType = confirmMessage.getMessageType();
            if(messageType!=null && messageType.equals(Constants.MessageType.CONFIRM)){
                // Handle Confirm Message
                timeLineViewModel.confirmMessage(confirmMessage);
                NotificationUtil.sendNotification(getApplicationContext());
            }
        }));


        applicationViewModel.getApplications().observe(this,(applications -> {
            if(applications.size()>0){
                navigationMenu.getOrCreateBadge(R.id.navigation_address_book).setNumber(applications.size());
            } else {
                BadgeDrawable badgeDrawable = navigationMenu.getBadge(R.id.navigation_address_book);
                if(badgeDrawable!=null){
                    badgeDrawable.setVisible(false);
                    navigationMenu.removeBadge(R.id.navigation_address_book);
                }
            }
        }));

        chatViewModel.getChatsLiveData().observe(this,(chats)->{
            long totalUnReadCount = chats.stream().mapToLong((chat -> {
                return chat.getUnReadCount();
            })).sum();
            if(totalUnReadCount>0){
                navigationMenu.getOrCreateBadge(R.id.navigation_chat).setNumber((int) totalUnReadCount);
            } else {
                BadgeDrawable badgeDrawable = navigationMenu.getBadge(R.id.navigation_chat);
                if(badgeDrawable!=null){
                    badgeDrawable.setVisible(false);
                    navigationMenu.removeBadge(R.id.navigation_chat);
                }
            }
        });


        // 初始化websocket
//        WebSocket.initSocket();




    }
}
