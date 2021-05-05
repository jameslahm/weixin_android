package com.MobileCourse.ViewModels;


import android.app.SearchableInfo;
import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.MobileCourse.Api.Resource;
import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.UserRepository;

import java.net.ResponseCache;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class SearchNewFriendViewModel extends ViewModel {
    private static String tag = "SearchNewFriendViewModel";

    private MediatorLiveData<User> resultLiveData = new MediatorLiveData<>();
    private UserRepository userRepository;

    @Inject
    public SearchNewFriendViewModel(@ApplicationContext Context context){
        this.userRepository = UserRepository.getInstance(context);
    }

    public LiveData<User> getResultLiveData(){
        return resultLiveData;
    }

    public void searchNewFriend(String weixinId){
        LiveData<Resource<User>> searchResult = userRepository.getUserByWeixinId(weixinId);
        resultLiveData.addSource(searchResult,(resource)->{
            if(resource!=null){
                switch (resource.status){
                    case LOADING:{
                        break;
                    }
                    case ERROR:{
                        resultLiveData.postValue(null);
                        resultLiveData.removeSource(searchResult);
                        break;
                    }
                    case SUCCESS:{
                        resultLiveData.postValue(resource.data);
                        resultLiveData.removeSource(searchResult);
                        break;
                    }
                }
            }
        });
    }
}
