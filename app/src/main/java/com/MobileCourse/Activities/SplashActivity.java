package com.MobileCourse.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.MobileCourse.Fragments.ChatFragment;
import com.MobileCourse.Fragments.ContactFragment;
import com.MobileCourse.Fragments.FindFragment;
import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.MeRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.MobileCourse.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LiveData<User> me =MeRepository.getInstance(getApplicationContext()).getMe();
        if(me.getValue()==null){
            Intent intent = new Intent(this,AuthActivity.class);
            startActivity(intent);
        }
    }
}
