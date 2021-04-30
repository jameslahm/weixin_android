package com.MobileCourse.ViewModels;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.MobileCourse.Api.Resource;
import com.MobileCourse.Models.Friend;
import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.MeRepository;
import com.MobileCourse.Repositorys.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class FriendsViewModel extends ViewModel {

    private MeRepository meRepository;
    private UserRepository userRepository;
    private LiveData<User> me;
    private MediatorLiveData<List<User>> friends = new MediatorLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Inject
    public FriendsViewModel(@ApplicationContext Context context){
        this.meRepository = MeRepository.getInstance(context);
        this.userRepository = UserRepository.getInstance(context);
        me = this.meRepository.getMe();
        friends.addSource(me,(user)->{
            if(user==null){
                friends.setValue(new ArrayList<>());
            } else {
                List<Friend> friendList =  user.getFriends();
                List<String> friendIds = friendList.stream().map((friend -> {
                    return friend.getId();
                })).collect(Collectors.toList());
                friends.addSource(userRepository.getUsersByIds(friendIds),(listResource -> {
                    switch (listResource.status){
                        case SUCCESS:
                        {
                            friends.setValue(listResource.data);
                            break;
                        }
                        case ERROR:
                        {
                            Toast.makeText(context, "GET Friends Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
            }
        });
    }

    public LiveData<List<User>> getFriends() {
        return friends;
    }
}
