package com.MobileCourse.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStoreOwner;

import com.MobileCourse.Adapters.DiscoverAdapter;
import com.MobileCourse.Api.ApiService;
import com.MobileCourse.Api.Resource;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.DiscoverResponse;
import com.MobileCourse.Models.Discover;
import com.MobileCourse.Repositorys.DiscoverRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class DiscoverViewModel extends ViewModel {
    private DiscoverRepository discoverRepository;

    @Inject
    public DiscoverViewModel(@ApplicationContext Context context){
        this.discoverRepository = DiscoverRepository.getInstance(context);
    }

    public LiveData<Resource<List<Discover>>> getDiscovers(){
        return discoverRepository.getDiscovers();
    }

    public LiveData<Resource<Discover>> likeDiscover(String discoverId){
        return discoverRepository.likeDiscover(discoverId);
    }

    public LiveData<Resource<Discover>> unLikeDiscover(String discoverId){
        return discoverRepository.unLikeDiscover(discoverId);
    }

    public LiveData<Resource<Discover>> replyDiscover(String discoverId,String content){
        return discoverRepository.replyDiscover(discoverId,content);
    }

    public LiveData<Resource<Discover>> createDiscover(String content,List<String> images,String video){
        return discoverRepository.createDiscover(content,images,video);
    }

}
