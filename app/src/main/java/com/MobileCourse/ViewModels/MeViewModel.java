package com.MobileCourse.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.MobileCourse.Models.User;
import com.MobileCourse.Repositorys.MeRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.internal.InjectedFieldSignature;

@HiltViewModel
public class MeViewModel extends ViewModel {
    private MeRepository meRepository;

    @Inject
    public MeViewModel(@ApplicationContext Context context){
        this.meRepository = MeRepository.getInstance(context);
    }

    public LiveData<User> getMe(){
        return this.meRepository.getMe();
    }
}
