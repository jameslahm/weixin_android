package com.MobileCourse;

import android.os.Bundle;
import android.util.Log;

import com.MobileCourse.Fragments.ChatFragment;
import com.MobileCourse.Fragments.ContactFragment;
import com.MobileCourse.Fragments.FindFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;


public class TemplateActivity1 extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigationMenu;

    FragmentManager fm = getSupportFragmentManager();

    Fragment fragment1 = new ChatFragment();
    Fragment fragment2 = new ContactFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        // 自动绑定view
        ButterKnife.bind(this);

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.content, fragment1);
        transaction.add(R.id.content, fragment2);
        transaction.show(fragment1).hide(fragment2).commit();

        navigationMenu.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction trans = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_chat:
                    trans.show(fragment1).hide(fragment2).commit();
                    return true;
                case R.id.navigation_address_book:
                    trans.show(fragment2).hide(fragment1).commit();
                    return true;
            }
            return false;
        });

        Log.e("test", "tst");
    }
}
