package com.MobileCourse.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.MobileCourse.Api.Resource;
import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.UserRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class ProfileViewModel extends ViewModel {

    private UserRepository userRepository;

    @Inject
    public ProfileViewModel(@ApplicationContext Context context){
        this.userRepository = UserRepository.getInstance(context);
    }

    public LiveData<Resource<User>> getProfileByWeixinId(String weixinId){
        return userRepository.getUserByWeixinId(weixinId);
    }

    public LiveData<User> getProfileById(String id){
        return userRepository.getUserById(id);
    }

}
