package com.MobileCourse.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.MobileCourse.Api.Resource;
import com.MobileCourse.Models.Application;
import com.MobileCourse.Models.ApplicationMessage;
import com.MobileCourse.Models.Message;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.ApplicationRepository;
import com.MobileCourse.Repositorys.TimeLineRepository;
import com.MobileCourse.Repositorys.UserRepository;
import com.MobileCourse.Utils.Constants;
import com.MobileCourse.Utils.MiscUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class ApplicationViewModel extends ViewModel {
    ApplicationRepository applicationRepository;
    UserRepository userRepository;

    TimeLineRepository timeLineRepository;

    @Inject
    public ApplicationViewModel(@ApplicationContext Context context){
        this.applicationRepository = ApplicationRepository.getInstance(context);
        this.timeLineRepository = TimeLineRepository.getInstance(context);
        this.userRepository = UserRepository.getInstance(context);
    }

    public LiveData<List<Application>> getApplications(){
        return applicationRepository.getApplications();
    }

    public void insertApplication(ApplicationMessage applicationMessage){
        applicationRepository.insertApplication(applicationMessage);
    }

    public void updateRead(){
        applicationRepository.updateRead();
    }

    public LiveData<Resource<User>> confirmApplication(Application application){
        //TODO FIXME add friend
        TimeLine timeLine = TimeLine.fromApplication(application);
        this.timeLineRepository.insertTimeLine(timeLine);
        return userRepository.confirmAddFriend(application.getFrom());
    }

}
