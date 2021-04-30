package com.MobileCourse.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.MobileCourse.Models.Application;
import com.MobileCourse.Models.ApplicationMessage;
import com.MobileCourse.Repositorys.ApplicationRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class ApplicationViewModel extends ViewModel {
    ApplicationRepository applicationRepository;

    @Inject
    public ApplicationViewModel(@ApplicationContext Context context){
        this.applicationRepository = ApplicationRepository.getInstance(context);
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

}
