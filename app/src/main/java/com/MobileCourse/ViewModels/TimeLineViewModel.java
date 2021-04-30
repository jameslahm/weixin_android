package com.MobileCourse.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.MobileCourse.Daos.TimeLineDao;
import com.MobileCourse.Models.Message;
import com.MobileCourse.Models.TimeLine;
import com.MobileCourse.Repositorys.TimeLineRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class TimeLineViewModel extends ViewModel {
    private TimeLineRepository timeLineRepository;

    @Inject
    public TimeLineViewModel(@ApplicationContext Context context){
        this.timeLineRepository = TimeLineRepository.getInstance(context);
    }


    public void insertTimeLine(TimeLine timeLine){
        timeLineRepository.insertTimeLine(timeLine);
    }

    public void insertMessage(Message message){
        LiveData<TimeLine> timeLineLiveData = timeLineRepository.getTimeLine(message.getFrom());

        Observer observer = new Observer<TimeLine>() {
            @Override
            public void onChanged(TimeLine timeLine) {
                timeLineRepository.insertMessage(timeLine,message);
                timeLineLiveData.removeObserver(this);
            }
        };

        timeLineLiveData.observeForever(observer);
    }
}
