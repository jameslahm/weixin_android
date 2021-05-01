package com.MobileCourse.ViewModels;


import android.content.Context;
import android.provider.MediaStore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.MobileCourse.Api.ApiService;
import com.MobileCourse.Api.Request.InviteInToGroupRequest;
import com.MobileCourse.Api.Resource;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.CommonResponse;
import com.MobileCourse.Api.Response.GroupResponse;
import com.MobileCourse.Models.Group;
import com.MobileCourse.Models.GroupDetail;
import com.MobileCourse.Models.InviteInToGroupMessage;
import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.GroupRepository;
import com.MobileCourse.Repositorys.TimeLineRepository;
import com.MobileCourse.Repositorys.UserRepository;

import java.util.List;


import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class GroupViewModel extends ViewModel {
    private static String tag = "GroupViewModel";
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private TimeLineRepository timeLineRepository;

    private MediatorLiveData<GroupDetail> groupDetailLiveData = new MediatorLiveData<>();

    @Inject
    public GroupViewModel(@ApplicationContext Context context){
        groupRepository = GroupRepository.getInstance(context);
        userRepository = UserRepository.getInstance(context);
        timeLineRepository = TimeLineRepository.getInstance(context);
    }

    public void setGroupId(String groupId){
        LiveData<Group> groupLiveData = groupRepository.getGroupById(groupId);
        groupDetailLiveData.addSource(groupLiveData,(group -> {
            LiveData<Resource<List<User>>> resourceLiveData  = userRepository.getUsersByIds(group.getMembers());
            groupDetailLiveData.addSource(resourceLiveData,(resource)->{
                if(resource.status== Resource.Status.SUCCESS){
                    List<User> members = resource.data;
                    GroupDetail groupDetail = GroupDetail.fromGroupAndMembers(group,members);
                    groupDetailLiveData.setValue(groupDetail);
                }
            });
        }));
    }

    public LiveData<GroupDetail> getGroup(String groupId){
        return groupDetailLiveData;
    }

    public void exitGroup(String groupId){
        // First exit
        groupRepository.exitGroup(groupId).observeForever((commonResponseApiResponse)->{
            if(commonResponseApiResponse instanceof ApiResponse.ApiSuccessResponse){
                timeLineRepository.deleteTimeLine(groupId);
            }
        });
        // Delete TimeLine
    }

    public LiveData<Resource<Group>> inviteInToGroup(String groupId,List<String> members){
        return groupRepository.inviteInToGroup(groupId,members);
    }
}
