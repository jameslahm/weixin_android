package com.MobileCourse;

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
import com.MobileCourse.Utils.EventListenerUtil;
import com.MobileCourse.Utils.WebSocket;
import com.MobileCourse.ViewModels.FriendsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.navigation)
    BottomNavigationView navigationMenu;

    public static Handler msgHandler;


    @SuppressLint("HandlerLeak")
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


        // 初始化websocket
        WebSocket.initSocket();
    }
}
