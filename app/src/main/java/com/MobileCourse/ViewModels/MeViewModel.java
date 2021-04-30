package com.MobileCourse.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.MobileCourse.Api.Resource;
import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.MeRepository;
import com.MobileCourse.Repositorys.UserRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.internal.InjectedFieldSignature;
import io.reactivex.Flowable;

@HiltViewModel
public class MeViewModel extends ViewModel {
    private MeRepository meRepository;
    private UserRepository userRepository;

    @Inject
    public MeViewModel(@ApplicationContext Context context){
        this.meRepository = MeRepository.getInstance(context);
        this.userRepository = UserRepository.getInstance(context);
    }

    public LiveData<User> getMe(){
        return this.meRepository.getMe();
    }

    public LiveData<Resource<User>> updateUser(String id, String weixinId, String username) {
        return this.userRepository.updateUser(id,weixinId,username);
    }

    public void logOut(){
        meRepository.deleteMe();
    }

    public void applyAddFriend(String friendId,String content){
        userRepository.addFriend(friendId,content);
    }
}
