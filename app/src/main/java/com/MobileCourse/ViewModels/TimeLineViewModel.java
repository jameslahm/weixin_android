package com.MobileCourse.ViewModels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.MobileCourse.Daos.TimeLineDao;
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
}
