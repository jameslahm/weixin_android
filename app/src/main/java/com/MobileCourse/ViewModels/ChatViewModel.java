package com.MobileCourse.ViewModels;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStoreOwner;

import com.MobileCourse.Models.Chat;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.ChatRepository;
import com.MobileCourse.Repositorys.MeRepository;
import com.MobileCourse.Repositorys.TimeLineRepository;
import com.MobileCourse.Repositorys.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class ChatViewModel extends ViewModel {
    private MeRepository meRepository;
    private UserRepository userRepository;
    private TimeLineRepository timeLineRepository;
    private ChatRepository chatRepository;

    private LiveData<User> me;
    private MediatorLiveData<List<Chat>> chatsLiveData = new MediatorLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Inject
    public ChatViewModel(@ApplicationContext Context context){
        this.meRepository  = MeRepository.getInstance(context);
        this.userRepository = UserRepository.getInstance(context);
        this.timeLineRepository = TimeLineRepository.getInstance(context);
        this.chatRepository = ChatRepository.getInstance(context);

        me = this.meRepository.getMe();

        chatsLiveData.addSource(me,(user -> {
            if(user==null){
                chatsLiveData.setValue(new ArrayList<>());
            } else {
                LiveData<List<TimeLine>> timeLinesLiveData =  timeLineRepository.getTimeLines();
                chatsLiveData.addSource(timeLinesLiveData,(timeLines)->{
                    List<Chat> newChats =  timeLines.stream().map(timeLine -> {
                       return this.chatRepository.getChatByTimeLine(timeLine);
                    }).collect(Collectors.toList());
                    chatsLiveData.setValue(newChats);
                });
            }
        }));
    }

    public LiveData<List<Chat>> getChatsLiveData(){
        return chatsLiveData;
    }


}
