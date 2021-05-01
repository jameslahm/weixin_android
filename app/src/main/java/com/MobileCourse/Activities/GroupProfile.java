package com.MobileCourse.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Binds;
import dagger.hilt.android.AndroidEntryPoint;

import com.MobileCourse.Adapters.AvatarAdapter;
import com.MobileCourse.Adapters.ChatAdapter;
import com.MobileCourse.Api.Resource;
import com.MobileCourse.Fragments.NewGroupFragment;
import com.MobileCourse.Models.GroupDetail;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.ViewModels.GroupViewModel;

import java.util.ArrayList;
import java.util.List;

@AndroidEntryPoint
public class GroupProfile extends AppCompatActivity {
    public static final String tag = "GroupProfile";

    public static final String GROUP_ID_KEY = "GROUP_ID_KEY";

    @BindView(R.id.avatarGrid)
    GridView avatarGrid;

    @BindView(R.id.exitGroup)
    ViewGroup exitGroupViewGroup;

    @BindView(R.id.inviteInToGroup)
    ViewGroup inviteInToGroupViewGroup;

    GroupViewModel groupViewModel;

    ArrayList<User> members = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_group);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String groupId = intent.getStringExtra(GROUP_ID_KEY);

        AvatarAdapter avatarAdapter = new AvatarAdapter(getApplicationContext(),members);
        avatarGrid.setAdapter(avatarAdapter);

        avatarGrid.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id)->{
            User user = members.get(position);
            Intent intent1 = new Intent(getApplicationContext(),ProfileActivity.class);
            intent1.putExtra(ProfileActivity.USER_WEIXIN_ID_KEY,user.getWeixinId());
            startActivity(intent1);
        });

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.getGroup(groupId).observe(this,(groupDetail)->{
            avatarAdapter.clear();
            avatarAdapter.addAll(groupDetail.getMembers());
            avatarAdapter.notifyDataSetChanged();
        });
        groupViewModel.setGroupId(groupId);

        exitGroupViewGroup.setOnClickListener((view)->{
            groupViewModel.exitGroup(groupId);
        });

        inviteInToGroupViewGroup.setOnClickListener(view->{
            NewGroupFragment.display("邀请加入群聊",(String text,List<String> members)->{
                groupViewModel.inviteInToGroup(groupId,members).observe(this,(resource)->{
                    if(resource.status== Resource.Status.SUCCESS){

                    }
                });
            },getSupportFragmentManager(),false);
        });
    }

}
