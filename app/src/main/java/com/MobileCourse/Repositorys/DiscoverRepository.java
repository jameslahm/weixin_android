package com.MobileCourse.Repositorys;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.MobileCourse.Api.ApiService;
import com.MobileCourse.Api.NetworkBoundResource;
import com.MobileCourse.Api.Request.CreateDiscoverRequest;
import com.MobileCourse.Api.Request.LikeDiscoverRequest;
import com.MobileCourse.Api.Request.ReplyDiscoverRequest;
import com.MobileCourse.Api.Request.UnLikeDiscoverRequest;
import com.MobileCourse.Api.Resource;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.DiscoverResponse;
import com.MobileCourse.Api.Response.GetDiscovesrResponse;
import com.MobileCourse.Daos.DiscoverDao;
import com.MobileCourse.Database.WeiXinDatabase;
import com.MobileCourse.Models.Discover;
import com.MobileCourse.Utils.AppExecutors;
import com.MobileCourse.Utils.MiscUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DiscoverRepository {
    private static DiscoverRepository instance;
    private DiscoverDao discoverDao;

    public static DiscoverRepository getInstance(Context context){
        if(instance==null){
            instance = new DiscoverRepository(context);
        }
        return instance;
    }

    private DiscoverRepository(Context context){
        discoverDao = WeiXinDatabase.getInstance(context).getDiscoverDao();
    }

    public LiveData<Resource<List<Discover>>> getDiscovers(){
        return new NetworkBoundResource<List<Discover>, GetDiscovesrResponse>(AppExecutors.getInstance()){
            @NotNull
            @Override
            protected LiveData<List<Discover>> loadFromDb() {
                return discoverDao.getAllDiscovers();
            }

            @Override
            protected boolean shouldFetch(@NotNull List<Discover> data) {
                return true;
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<GetDiscovesrResponse>> createCall() {
                return ApiService.getDiscoverApi().getDiscovers();
            }

            @Override
            protected void saveCallResult(@NotNull GetDiscovesrResponse getDiscovesrResponse) {
                super.saveCallResult(getDiscovesrResponse);
                List<Discover> discovers = getDiscovesrResponse.getDiscovers();
                discoverDao.insertDiscovers(discovers);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<Discover>> likeDiscover(String discoverId){
        return new NetworkBoundResource<Discover, DiscoverResponse>(AppExecutors.getInstance()){
            @NotNull
            @Override
            protected LiveData<Discover> loadFromDb() {
                return discoverDao.getDiscoverById(discoverId);
            }

            @Override
            protected boolean shouldFetch(@NotNull Discover data) {
                return true;
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<DiscoverResponse>> createCall() {
                return ApiService.getDiscoverApi().likeDiscover(new LikeDiscoverRequest(
                        discoverId
                ));
            }

            @Override
            protected void saveCallResult(@NotNull DiscoverResponse discoverResponse) {
                super.saveCallResult(discoverResponse);
                discoverDao.insertDiscover(Discover.fromDiscoverResponse(discoverResponse));
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<Discover>> unLikeDiscover(String discoverId){
        return new NetworkBoundResource<Discover, DiscoverResponse>(AppExecutors.getInstance()){
            @NotNull
            @Override
            protected LiveData<Discover> loadFromDb() {
                return discoverDao.getDiscoverById(discoverId);
            }

            @Override
            protected boolean shouldFetch(@NotNull Discover data) {
                return true;
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<DiscoverResponse>> createCall() {
                return ApiService.getDiscoverApi().unLikeDiscover(new UnLikeDiscoverRequest(
                        discoverId
                ));
            }

            @Override
            protected void saveCallResult(@NotNull DiscoverResponse discoverResponse) {
                super.saveCallResult(discoverResponse);
                discoverDao.insertDiscover(Discover.fromDiscoverResponse(discoverResponse));
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<Discover>> replyDiscover(String discoverId,String content){
        return new NetworkBoundResource<Discover, DiscoverResponse>(AppExecutors.getInstance()){
            @NotNull
            @Override
            protected LiveData<Discover> loadFromDb() {
                return discoverDao.getDiscoverById(discoverId);
            }

            @Override
            protected boolean shouldFetch(@NotNull Discover data) {
                return true;
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<DiscoverResponse>> createCall() {
                return ApiService.getDiscoverApi().replyDiscover(new ReplyDiscoverRequest(
                        discoverId,content, MiscUtil.getCurrentTimestamp()
                ));
            }

            @Override
            protected void saveCallResult(@NotNull DiscoverResponse discoverResponse) {
                super.saveCallResult(discoverResponse);
                discoverDao.insertDiscover(Discover.fromDiscoverResponse(discoverResponse));
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<Discover>> createDiscover(String content,List<String> images,String video){
        // TODO FIXME
        final String[] disoverId = {content};
        return new NetworkBoundResource<Discover, DiscoverResponse>(AppExecutors.getInstance()){
            @NotNull
            @Override
            protected LiveData<Discover> loadFromDb() {
                return discoverDao.getDiscoverById(disoverId[0]);
            }

            @Override
            protected boolean shouldFetch(@NotNull Discover data) {
                return true;
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<DiscoverResponse>> createCall() {
                return ApiService.getDiscoverApi().createDiscover(new CreateDiscoverRequest(
                        content,images,video,MiscUtil.getCurrentTimestamp()
                ));
            }

            @Override
            protected void saveCallResult(@NotNull DiscoverResponse discoverResponse) {
                super.saveCallResult(discoverResponse);
                disoverId[0] = discoverResponse.getId();
                discoverDao.insertDiscover(Discover.fromDiscoverResponse(discoverResponse));
            }
        }.getAsLiveData();

    }
}
