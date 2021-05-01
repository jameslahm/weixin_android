package com.MobileCourse.ViewModels;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.MobileCourse.Api.Response.GroupResponse;
import com.MobileCourse.Daos.TimeLineDao;
import com.MobileCourse.Models.ConfirmMessage;
import com.MobileCourse.Models.Friend;
import com.MobileCourse.Models.InviteInToGroupMessage;
import com.MobileCourse.Models.Me;
import com.MobileCourse.Models.Message;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.GroupRepository;
import com.MobileCourse.Repositorys.MeRepository;
import com.MobileCourse.Repositorys.TimeLineRepository;
import com.MobileCourse.Repositorys.UserRepository;
import com.MobileCourse.Utils.AppExecutors;
import com.MobileCourse.Utils.Constants;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class TimeLineViewModel extends ViewModel {
    private TimeLineRepository timeLineRepository;
    private UserRepository userRepository;
    private MeRepository meRepository;
    private GroupRepository groupRepository;

    @Inject
    public TimeLineViewModel(@ApplicationContext Context context){
        this.timeLineRepository = TimeLineRepository.getInstance(context);
        this.userRepository = UserRepository.getInstance(context);
        this.meRepository = MeRepository.getInstance(context);
        this.groupRepository = GroupRepository.getInstance(context);
    }


    public void insertTimeLine(TimeLine timeLine){
        timeLineRepository.insertTimeLine(timeLine);
    }

    public void insertMessage(Message message){
        LiveData<TimeLine> timeLineLiveData;
        if(message.getMessageType().equals(Constants.MessageType.GROUP)){
            timeLineLiveData = timeLineRepository.getTimeLine(message.getTo());
        } else {
            timeLineLiveData = timeLineRepository.getTimeLine(message.getFrom());
        }
        Observer observer = new Observer<TimeLine>() {
            @Override
            public void onChanged(TimeLine timeLine) {
                timeLineRepository.insertMessage(timeLine,message);
                timeLineLiveData.removeObserver(this);
            }
        };

        AppExecutors.getInstance().getMainThread().execute(()->{
            timeLineLiveData.observeForever(observer);
        });

    }


    // TODO FIXME insertGroup
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void inviteInToGroup(InviteInToGroupMessage inviteInToGroupMessage){
        TimeLine timeLine = TimeLine.fromInviteInToGroupMessage(inviteInToGroupMessage);
        insertTimeLine(timeLine);
        groupRepository.insertGroup(inviteInToGroupMessage.getGroup());
    }

    public void confirmMessage(ConfirmMessage message){
        TimeLine timeLine = TimeLine.fromConfirmMessage(message);
        timeLineRepository.insertTimeLine(timeLine);

        User me = meRepository.getMe().getValue();
        AppExecutors.getInstance().getDiskIO().execute(()->{
            List<Friend> friends = me.getFriends();
            Friend newFriend = new Friend();
            newFriend.setId(message.getFrom());
            newFriend.setNickName(message.getUser().getUsername());

            // FIXME add timelinesaved
            friends.add(newFriend);

            userRepository.insertUser(me);
            meRepository.insertMe(new Me(me.getId()));
            // TODO Get TimeLineSavedId
        });
    }

    public void updateLastCheckTimestamp(String timeLineId,long timestamp){
        timeLineRepository.updateLastCheckTimestamp(timeLineId,timestamp);
    }

    public LiveData<TimeLine> getTimeLineById(String id){
        return timeLineRepository.getTimeLine(id);
    }
}
