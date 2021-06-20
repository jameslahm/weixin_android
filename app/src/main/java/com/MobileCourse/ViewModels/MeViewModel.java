package com.MobileCourse.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.MobileCourse.Api.Resource;
import com.MobileCourse.Api.Response.TimeLineResponse;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.MeRepository;
import com.MobileCourse.Repositorys.TimeLineRepository;
import com.MobileCourse.Repositorys.UserRepository;
import com.google.android.exoplayer2.Timeline;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class MeViewModel extends ViewModel {
    private MeRepository meRepository;
    private UserRepository userRepository;
    private TimeLineRepository timeLineRepository;

    @Inject
    public MeViewModel(@ApplicationContext Context context){
        this.meRepository = MeRepository.getInstance(context);
        this.userRepository = UserRepository.getInstance(context);
        this.timeLineRepository = TimeLineRepository.getInstance(context);
    }

    public LiveData<User> getMe(){
        return this.meRepository.getMe();
    }

    public LiveData<Resource<User>> updateUser(String id, String weixinId, String username,String avatar,String password) {
        return this.userRepository.updateUser(id,weixinId,username,avatar,password);
    }

    public void logOut(){
        meRepository.deleteMe();
    }

    public void applyAddFriend(String friendId,String content){
        userRepository.applyAddFriend(friendId,content);
    }

    public LiveData<Resource<User>> deleteFriend(User user){
        this.timeLineRepository.deleteTimeLine(user.getId());
        return userRepository.deleteFriend(user);
    }
}
