package com.MobileCourse.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.Repositorys.UserRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterFragment extends Fragment {
    @BindView(R.id.registerWeixinId)
    EditText weixinIdEditText;

    @BindView(R.id.registerPassword)
    EditText passwordEditText;

    @BindView(R.id.registerUsername)
    EditText usernameEditText;

    @BindView(R.id.swipeLeftImg)
    ImageView swipeLeftImageView;

    @BindView(R.id.registerButton)
    Button registerButton;

    boolean checked = false;

    ViewPager viewPager;

    public RegisterFragment(ViewPager viewPager){
        this.viewPager = viewPager;
    }

    public void init(){
        registerButton.setOnClickListener((View view)->{
            String weixinId = weixinIdEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            LiveData<Resource<User>> resourceLiveData =  UserRepository.getInstance(getContext()).register(weixinId,username,password);
            resourceLiveData.observe(getViewLifecycleOwner(),(resource)->{
                if(resource!=null){
                    if(checked){
                        return;
                    }
                    switch (resource.status){
                        case ERROR:
                        {
                            Toast.makeText(getContext(),"注册失败",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case SUCCESS:
                        {
                            viewPager.setCurrentItem(0);
                            Toast.makeText(getContext(), "注册成功，请登录", Toast.LENGTH_SHORT).show();
                            checked = true;
                            break;
                        }

                    }
                }

            });
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_register,container,false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }
}
