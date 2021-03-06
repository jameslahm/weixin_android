package com.MobileCourse.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.viewpager.widget.ViewPager;

import com.MobileCourse.Api.Resource;
import com.MobileCourse.Gestures.OnSwipeTouchListener;
import com.MobileCourse.MainActivity;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.Repositorys.MeRepository;
import com.MobileCourse.Repositorys.UserRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.MobileCourse.MainActivity;

public class LoginFragment extends Fragment {
    @BindView(R.id.loginWeixinId)
    EditText weixinIdEditText;

    @BindView(R.id.loginPassword)
    EditText passwordEditText;

    @BindView(R.id.loginButton)
    Button loginButton;

    @BindView(R.id.swipeRightImg)
    ImageView swipeRightImageView;

    boolean checked = false;

    ViewPager viewPager;

    public LoginFragment(ViewPager viewPager){
        this.viewPager = viewPager;
    }

    public void init(){
        loginButton.setOnClickListener((View view)->{
            String weixinId = weixinIdEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            LiveData<Resource<User>> resourceLiveData =  UserRepository.getInstance(getContext()).login(weixinId,password);
            resourceLiveData.observe(getViewLifecycleOwner(),(resource)->{
                if(resource!=null){
                    if(checked){
                        return;
                    }
                    switch (resource.status){
                        case ERROR:
                        {
                            Toast.makeText(getContext(),"????????????",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case SUCCESS:
                        {
                            Toast.makeText(getContext(),"????????????",Toast.LENGTH_SHORT).show();
                            checked = true;
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                        }

                    }
                }

            });
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login,container,false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }
}
