package com.MobileCourse;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.MobileCourse.Fragments.Fragment1;
import com.MobileCourse.Fragments.Fragment2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TemplateActivity1 extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigationMenu;

    FragmentManager fm = getSupportFragmentManager();

    Fragment fragment1 = new Fragment1();
    Fragment fragment2 = new Fragment2(false);

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
                case R.id.navigation1:
                    trans.show(fragment1).hide(fragment2).commit();
                    return true;
                case R.id.navigation2:
                    trans.show(fragment2).hide(fragment1).commit();
                    return true;
            }
            return false;
        });

        Log.e("test", "tst");
    }
}
