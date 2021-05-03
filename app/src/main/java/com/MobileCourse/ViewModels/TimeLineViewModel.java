package com.MobileCourse.ViewModels;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.MobileCourse.Api.Resource;
import com.MobileCourse.Api.Response.GroupResponse;
import com.MobileCourse.Daos.TimeLineDao;
import com.MobileCourse.Models.ConfirmMessage;
import com.MobileCourse.Models.Friend;
import com.MobileCourse.Models.Group;
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
import com.MobileCourse.Utils.MiscUtil;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import hilt_aggregated_deps._com_MobileCourse_Activities_AuthActivity_GeneratedInjector;

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

    public void insertMessage(Message message,User me){
        LiveData<TimeLine> timeLineLiveData;
        if(message.getMessageType().equals(Constants.MessageType.GROUP)){
            timeLineLiveData = timeLineRepository.getTimeLineById(message.getTo());
        } else {
            if(me.getId().equals(message.getTo())) {
                timeLineLiveData = timeLineRepository.getTimeLineById(message.getFrom());
            } else {
                timeLineLiveData = timeLineRepository.getTimeLineById(message.getTo());
            }
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
//            meRepository.insertMe(new Me(me.getId()));
        });
    }

    public void updateLastCheckTimestamp(String timeLineId,long timestamp){
        timeLineRepository.updateLastCheckTimestamp(timeLineId,timestamp);
    }

    public LiveData<TimeLine> getTimeLineById(String id){
        return timeLineRepository.getTimeLineById(id);
    }

    public void insertTimeLineByUser(User user){
        TimeLine timeLine = new TimeLine(
                user.getId(),
                user.getUsername(),
                "",
                user.getAvatar(),
                MiscUtil.formatTimestamp(MiscUtil.getCurrentTimestamp()),
                MiscUtil.getCurrentTimestamp(),
                Constants.MessageType.SINGLE,
                new ArrayList<>()
        );
        insertTimeLine(timeLine);
    }

    public void insertTimeLineByGroup(Group group){
        TimeLine timeLine = new TimeLine(
                group.getId(),
                group.getName(),
                "",
                group.getAvatar(),
                MiscUtil.formatTimestamp(MiscUtil.getCurrentTimestamp()),
                MiscUtil.getCurrentTimestamp(),
                Constants.MessageType.GROUP,
                new ArrayList<>()
        );
        insertTimeLine(timeLine);
    }

    public LiveData<Resource<TimeLine>> syncTimeLineInSingle(User user){
        return timeLineRepository.syncTimeLineInSingle(user);
    }

    // only delete messages
    public void deleteTimeLineMessages(String id){
        timeLineRepository.deleteTimeLineMessages(id);
    }
}
