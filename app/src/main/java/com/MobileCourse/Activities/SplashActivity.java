package com.MobileCourse.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.Window;

import com.MobileCourse.Api.ApiService;
import com.MobileCourse.Fragments.ChatFragment;
import com.MobileCourse.Fragments.ContactFragment;
import com.MobileCourse.Fragments.FindFragment;
import com.MobileCourse.MainActivity;
import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.MeRepository;
import com.MobileCourse.ViewModels.MeViewModel;
import com.MobileCourse.WebSocket.MessageApi;
import com.MobileCourse.WebSocket.MessageService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;

import com.MobileCourse.R;
import androidx.lifecycle.ViewModelProvider;


import java.time.DayOfWeek;

@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {

    MeViewModel meViewModel;
    MessageApi messageApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ApiService.init(getApplicationContext());

        meViewModel = new ViewModelProvider(this).get(MeViewModel.class);;
        messageApi = MessageService.getInstance().getMessageApi();
        LiveData<User> meLiveData = meViewModel.getMe();

        meLiveData.observe(this, (user -> {
            if(user==null){
                Intent intent = new Intent(this,AuthActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }));

    }
}
