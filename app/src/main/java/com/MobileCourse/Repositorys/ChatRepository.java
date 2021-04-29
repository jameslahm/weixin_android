package com.MobileCourse.Repositorys;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.MobileCourse.Daos.MeDao;
import com.MobileCourse.Daos.TimeLineDao;
import com.MobileCourse.Daos.UserDao;
import com.MobileCourse.Database.WeiXinDatabase;
import com.MobileCourse.Models.Chat;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;
import com.MobileCourse.WeixinApplication;

import java.util.List;

public class ChatRepository {
    private static ChatRepository instance;
    private UserDao userDao;
    private TimeLineDao timeLineDao;

    public static ChatRepository getInstance(Context context){
        if(instance==null){
            instance = new ChatRepository(context);
        }
        return instance;
    }

    private ChatRepository(Context context){
        userDao = WeiXinDatabase.getInstance(context).getUserDao();
        timeLineDao = WeiXinDatabase.getInstance(context).getTimeLineDao();
    }

    public Chat getChatByTimeLine(TimeLine timeLine){
        return new Chat(timeLine.getTo(),timeLine.getName(),
                timeLine.getAvatar(),timeLine.getLastSpeak(),
                timeLine.getLastSpeakTime());
    }

}
