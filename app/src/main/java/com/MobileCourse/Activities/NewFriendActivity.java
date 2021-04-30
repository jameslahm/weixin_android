package com.MobileCourse.Activities;


import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Binds;
import dagger.hilt.android.AndroidEntryPoint;
import com.MobileCourse.R;
import com.MobileCourse.ViewModels.SearchNewFriendViewModel;
import com.bumptech.glide.Glide;

@AndroidEntryPoint
public class NewFriendActivity extends AppCompatActivity {

    @BindView(R.id.weixinId)
    EditText weixinIdEditText;

    @BindView(R.id.new_friend_res)
    ViewGroup newFriendResViewGroup;

    @BindView(R.id.new_friend_avatar)
    ImageView newFriendAvatarImageView;

    @BindView(R.id.new_friend_nickname)
    TextView newFriendNickNameTextView;

    private SearchNewFriendViewModel searchNewFriendViewModel;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        ButterKnife.bind(this);

        newFriendResViewGroup.setVisibility(View.INVISIBLE);

        searchNewFriendViewModel = new ViewModelProvider(this).get(SearchNewFriendViewModel.class);

        searchNewFriendViewModel.getResultLiveData().observe(this,(user)->{
            if(user==null){
                newFriendResViewGroup.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
            } else {
                newFriendResViewGroup.setVisibility(View.VISIBLE);
                Glide.with(this).load(user.getAvatar()).placeholder(R.drawable.avatar2).into(newFriendAvatarImageView);
                newFriendNickNameTextView.setText(user.getUsername());
            }
        });

        weixinIdEditText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchNewFriendViewModel.searchNewFriend(weixinIdEditText.getText().toString());
                    handled = true;
                }
                return handled;
            });
    }
}
