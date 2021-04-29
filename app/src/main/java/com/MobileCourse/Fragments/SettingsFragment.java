package com.MobileCourse.Fragments;
import com.MobileCourse.Activities.AuthActivity;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.ViewModels.MeViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsFragment  extends Fragment {

    @BindView(R.id.avatarMenu)
    ViewGroup avatarMenuViewGroup;

    @BindView(R.id.avatarImg)
    ImageView avatarImageView;

    @BindView(R.id.usernameMenu)
    ViewGroup usernameMenuViewGroup;

    @BindView(R.id.usernameText)
    TextView usernameTextView;

    @BindView(R.id.weixinIdMenu)
    ViewGroup weixinIdMenuViewGroup;

    @BindView(R.id.weixinIdText)
    TextView weixinIdTextView;


    MeViewModel meViewModel;

    public SettingsFragment(){

    }

    public void init(){
        meViewModel = new ViewModelProvider(this).get(MeViewModel.class);
        LiveData<User> meLiveData = meViewModel.getMe();
        meLiveData.observe(getViewLifecycleOwner(), (user -> {
            if(user==null){
                Intent intent = new Intent(getContext(), AuthActivity.class);
                startActivity(intent);
            } else {
                weixinIdTextView.setText(user.getWeixinId());
                usernameTextView.setText(user.getUsername());
                Glide.with(this).load("http://139.196.81.14:7998/upload/avatar1.jpeg").placeholder(R.drawable.avatar2)
                        .apply(RequestOptions.circleCropTransform()).into(avatarImageView);
            }
        }));

        usernameMenuViewGroup.setOnClickListener((view)->{
            FragmentManager fragmentManager = getFragmentManager();
            EditDialogFragment.display("设置名字",usernameTextView.getText().toString(),(text)->{
                Log.e("Hello",text);
            },fragmentManager);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }
}
