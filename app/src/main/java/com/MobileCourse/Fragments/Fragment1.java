package com.MobileCourse.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.MobileCourse.FalseWechat.FalseWeChat;
import com.MobileCourse.FalseWechat.FalseWeChat2;
import com.MobileCourse.R;

public class Fragment1 extends Fragment {

    View mView;

    @BindView(R.id.show_wechat)
    Button show_wechat;
    @BindView(R.id.show_wechat2)
    Button show_wechat2;

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_1, container, false);
        // 绑定视图
        ButterKnife.bind(this, mView);
        show_wechat.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), FalseWeChat.class);
            startActivity(intent);
        });
        show_wechat2.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), FalseWeChat2.class);
            startActivity(intent);
        });
        return mView;
    }
}
