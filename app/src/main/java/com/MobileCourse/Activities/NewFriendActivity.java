package com.MobileCourse.Activities;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Binds;
import dagger.hilt.android.AndroidEntryPoint;

import com.MobileCourse.Adapters.ApplicationAdapter;
import com.MobileCourse.Adapters.ChatAdapter;
import com.MobileCourse.Fragments.EditDialogFragment;
import com.MobileCourse.Models.Application;
import com.MobileCourse.R;
import com.MobileCourse.Utils.MiscUtil;
import com.MobileCourse.ViewModels.ApplicationViewModel;
import com.MobileCourse.ViewModels.MeViewModel;
import com.MobileCourse.ViewModels.SearchNewFriendViewModel;
import com.bumptech.glide.Glide;

@AndroidEntryPoint
public class NewFriendActivity extends AppCompatActivity {

    private static String TAG = "NewFriendActivity";

    @BindView(R.id.weixinId)
    EditText weixinIdEditText;

    @BindView(R.id.new_friend_res)
    ViewGroup newFriendResViewGroup;

    @BindView(R.id.new_friend_avatar)
    ImageView newFriendAvatarImageView;

    @BindView(R.id.new_friend_nickname)
    TextView newFriendNickNameTextView;

    @BindView(R.id.new_friends_recylerview)
    RecyclerView newFriendsRecyclerView;

    ApplicationViewModel applicationViewModel;

    private SearchNewFriendViewModel searchNewFriendViewModel;

    private MeViewModel meViewModel;

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
                MiscUtil.loadImage(newFriendAvatarImageView,user.getAvatar());
                newFriendNickNameTextView.setText(user.getUsername());
                newFriendResViewGroup.setOnClickListener((view)->{
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    intent.putExtra(ProfileActivity.USER_WEIXIN_ID_KEY,user.getWeixinId());
                    startActivity(intent);
                });
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

        applicationViewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        meViewModel = new ViewModelProvider(this).get(MeViewModel.class);

        applicationViewModel.updateRead();

        ApplicationAdapter applicationAdapter = new ApplicationAdapter(new ApplicationAdapter.ApplicationDiff(),
            (Application application) ->{
                applicationViewModel.confirmApplication(application).observe(this,(resource)->{
                    switch (resource.status){
                        case SUCCESS:{
                            break;
                        }
                        case ERROR:{
                            Log.e(TAG,"Error");
                        }
                    }
                });
            }
        );
        newFriendsRecyclerView.setAdapter(applicationAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        newFriendsRecyclerView.setLayoutManager(linearLayoutManager);


        applicationViewModel.getApplications().observe(this,(applications)->{
            applicationAdapter.submitList(applications);
        });

    }
}
