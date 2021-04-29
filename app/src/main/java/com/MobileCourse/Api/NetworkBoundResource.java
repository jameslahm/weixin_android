package com.MobileCourse.Api;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.CommonResponse;
import com.MobileCourse.Utils.AppExecutors;

import org.jetbrains.annotations.NotNull;

public abstract class NetworkBoundResource<CacheObject,RequestObject extends CommonResponse> {
    private AppExecutors appExecutors;
    private MediatorLiveData<Resource<CacheObject>> results = new MediatorLiveData<>();

    public NetworkBoundResource(AppExecutors appExecutors){
        this.appExecutors = appExecutors;
        init();
    }

    private void init(){
        results.setValue((Resource<CacheObject>)Resource.loading(null));
        final LiveData<CacheObject> dbSource = loadFromDb();
        results.addSource(dbSource,(cacheObject -> {
            results.removeSource(dbSource);
            if(shouldFetch(cacheObject)){
                fetchFromNetwork(dbSource);
            } else {
                results.addSource(dbSource,(cacheObject1 ->{
                    setValue(Resource.success(cacheObject1));
                } ));
            }
        }));
    }

    @NotNull @MainThread
    protected abstract LiveData<CacheObject> loadFromDb();

    @MainThread
    protected abstract boolean shouldFetch(@NotNull CacheObject data);

    private void fetchFromNetwork(final LiveData<CacheObject> dbSource){
        results.addSource(dbSource,(cacheObject -> {
            setValue(Resource.loading(cacheObject));
        }));

        final LiveData<ApiResponse<RequestObject>> apiResponse = createCall();

        results.addSource(apiResponse,(requestObjectApiResponse -> {
            results.removeSource(dbSource);
            results.removeSource(apiResponse);

            if(requestObjectApiResponse instanceof ApiResponse.ApiSuccessResponse){
                appExecutors.getDiskIO().execute(()->{
                    saveCallResult(processResponse((ApiResponse.ApiSuccessResponse) requestObjectApiResponse));
                    appExecutors.getMainThread().execute(()->{
                        results.addSource(loadFromDb(),(cacheObject -> {
                            setValue(Resource.success(cacheObject));
                        }));
                    });
                });
            } else {
                results.addSource(dbSource, new Observer<CacheObject>() {
                    @Override
                    public void onChanged(CacheObject cacheObject) {
                        setValue(Resource.error(cacheObject,((ApiResponse.ApiErrorResponse)requestObjectApiResponse).getErrorMessage()));
                    }
                });
            }
        }));


    }

    private RequestObject processResponse(ApiResponse.ApiSuccessResponse response){
        return (RequestObject) response.getBody();
    }

    private void setValue(Resource<CacheObject> newValue){
        if(results.getValue()!=newValue){
            results.setValue(newValue);
        }
    }

    @NotNull @MainThread
    protected LiveData<ApiResponse<RequestObject>> createCall(){
        return null;
    };

    @WorkerThread
    protected void saveCallResult(@NotNull RequestObject requestObject){

    };

    public final LiveData<Resource<CacheObject>> getAsLiveData(){
        return results;
    }
}
