package com.MobileCourse.Activities;

import android.os.Bundle;
import android.util.Log;

import com.MobileCourse.Adapters.FragmentAdapter;
import com.MobileCourse.Fragments.ChatFragment;
import com.MobileCourse.Fragments.ContactFragment;
import com.MobileCourse.Fragments.FindFragment;
import com.MobileCourse.Fragments.LoginFragment;
import com.MobileCourse.Fragments.RegisterFragment;
import com.MobileCourse.Fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.MobileCourse.R;

import java.util.ArrayList;
import java.util.List;

public class AuthActivity extends AppCompatActivity {
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    Fragment fragment1;
    Fragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // 自动绑定view
        ButterKnife.bind(this);

        fragment1 = new LoginFragment(viewPager);
        fragment2 = new RegisterFragment(viewPager);

        FragmentManager fm = getSupportFragmentManager();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LoginFragment(viewPager));
        fragments.add(new RegisterFragment(viewPager));

        viewPager.setAdapter(new FragmentAdapter(fm, fragments));
    }
}
