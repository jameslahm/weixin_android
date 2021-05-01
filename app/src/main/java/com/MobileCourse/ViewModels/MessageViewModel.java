package com.MobileCourse.ViewModels;

import android.content.Context;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.MobileCourse.Api.Resource;
import com.MobileCourse.Models.Group;
import com.MobileCourse.Models.Message;
import com.MobileCourse.Models.MessageDetail;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.GroupRepository;
import com.MobileCourse.Repositorys.MeRepository;
import com.MobileCourse.Repositorys.TimeLineRepository;
import com.MobileCourse.Repositorys.UserRepository;
import com.MobileCourse.Utils.Constants;
import com.MobileCourse.Utils.MiscUtil;
import com.MobileCourse.WebSocket.MessageService;
import com.MobileCourse.WebSocket.Request.CreateMessage;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class MessageViewModel extends ViewModel {
    private static String tag = "MessageViewModel";

    TimeLineRepository timeLineRepository;
    UserRepository userRepository;
    MeRepository meRepository;
    GroupRepository groupRepository;

    private MediatorLiveData<List<MessageDetail>> messageDetailMediatorLiveData =
            new MediatorLiveData<>();

    @Inject
    public MessageViewModel(@ApplicationContext Context context){
        timeLineRepository = TimeLineRepository.getInstance(context);
        userRepository = UserRepository.getInstance(context);
        meRepository = MeRepository.getInstance(context);
        groupRepository = GroupRepository.getInstance(context);
    }

    public LiveData<List<MessageDetail>> getMessageDetailsLiveData(){
        return messageDetailMediatorLiveData;
    }

    public void sendMessage(User me,TimeLine timeLine ,String content){
        CreateMessage message = new CreateMessage(
                content,
                Constants.ContentType.TEXT,
                timeLine.getMessageType(),
                timeLine.getId(),
                MiscUtil.getCurrentTimestamp()
        );
        Log.e(tag,message.toString());
        MessageService.getInstance().getMessageApi().sendMessage(message);
        timeLineRepository.insertMessage(timeLine,new Message(
                content,
                Constants.ContentType.TEXT,
                timeLine.getMessageType(),
                MiscUtil.getCurrentTimestamp(),
                me.getId(),
                timeLine.getId()
        ));
        timeLineRepository.updateLastCheckTimestamp(timeLine.getId(),MiscUtil.getCurrentTimestamp());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getMessageDetails(String timeLineId){
        LiveData<TimeLine> timeLineLiveData = timeLineRepository.getTimeLine(timeLineId);
        messageDetailMediatorLiveData.addSource(
                timeLineLiveData,
                (timeLine)->{
                    User me =meRepository.getMe().getValue();
                    if(me==null){
                        return;
                    }
                    String messageType = timeLine.getMessageType();
                    if(messageType.equals(Constants.MessageType.SINGLE) || messageType.equals(Constants.MessageType.CONFIRM)){
                        LiveData<User> target =  userRepository.getUserById(timeLine.getId());
                        messageDetailMediatorLiveData.addSource(target,(user -> {
                            List<MessageDetail> messageDetails = timeLine.getMessages().stream().map((message -> {
                                return MessageDetail.fromMessageAndUser(message,me,user);
                            })).collect(Collectors.toList());
                            messageDetailMediatorLiveData.setValue(messageDetails);
                        }));
                    } else {
                        LiveData<Group> target = groupRepository.getGroupById(timeLine.getId());
                        // TODO FIXME
                        messageDetailMediatorLiveData.addSource(target,(group -> {
                            LiveData<Resource<List<User>>> members =  userRepository.getUsersByIds(group.getMembers());
                            messageDetailMediatorLiveData.addSource(members,(resource)->{
                                if(resource.status== Resource.Status.SUCCESS){
                                    List<MessageDetail> messageDetails = timeLine.getMessages().stream().map((message -> {
                                        return MessageDetail.fromMessageAndGroup(message,me,resource.data);
                                    })).collect(Collectors.toList());
                                    messageDetailMediatorLiveData.setValue(messageDetails);
                                }
                            });
                        }));
                    }
                }
        );
    }

}
