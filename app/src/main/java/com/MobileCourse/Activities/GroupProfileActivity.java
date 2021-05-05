package com.MobileCourse.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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
import com.MobileCourse.Models.Group;
import com.MobileCourse.Models.GroupDetail;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.ViewModels.GroupViewModel;
import com.MobileCourse.ViewModels.TimeLineViewModel;

import java.util.ArrayList;
import java.util.List;

@AndroidEntryPoint
public class GroupProfileActivity extends AppCompatActivity {
    public static final String tag = "GroupProfile";

    public static final String GROUP_ID_KEY = "GROUP_ID_KEY";

    @BindView(R.id.avatarGrid)
    GridView avatarGrid;

    @BindView(R.id.exitGroup)
    ViewGroup exitGroupViewGroup;

    @BindView(R.id.inviteInToGroup)
    ViewGroup inviteInToGroupViewGroup;

    @BindView(R.id.deleteTimeLine)
    ViewGroup deleteTimeLineMessage;

    @BindView(R.id.syncTimeLine)
    ViewGroup syncTimeLine;

    GroupViewModel groupViewModel;

    TimeLineViewModel timeLineViewModel;

    ArrayList<User> members = new ArrayList<>();

    Group group;

    @BindView(R.id.iv_return)
    ImageView returnImageView;

    @RequiresApi(api = Build.VERSION_CODES.N)
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
        timeLineViewModel = new ViewModelProvider(this).get(TimeLineViewModel.class)
;
        groupViewModel.getGroup(groupId).observe(this,(groupDetail)->{
            avatarAdapter.clear();
            avatarAdapter.addAll(groupDetail.getMembers());
            avatarAdapter.notifyDataSetChanged();
            group = Group.fromGroupDetail(groupDetail);
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

        syncTimeLine.setOnClickListener((view)->{

        });

        deleteTimeLineMessage.setOnClickListener((view)->{
            timeLineViewModel.deleteTimeLineMessages(groupId);
        });

        syncTimeLine.setOnClickListener((view)->{
            timeLineViewModel.syncTimeLineInGroup(group).observe(this, (resource)->{
                if(resource!=null){
                    switch (resource.status){
                        case LOADING:{
                            break;
                        }
                        case SUCCESS:{
                            Toast.makeText(this, "同步成功", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case ERROR:{
                            Toast.makeText(this, "同步失败", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            });
        });

        returnImageView.setOnClickListener((view)->{
            finish();
        });
    }

}
