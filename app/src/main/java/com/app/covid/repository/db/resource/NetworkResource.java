package com.app.covid.repository.db.resource;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.app.covid.core.AppExecutors;
import com.app.covid.repository.db.network.ApiResponse;
import java.util.Objects;


public abstract class NetworkResource<ResultType> {
    private final AppExecutors appExecutors;

    private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        post();
    }

    public void post(){
        result = new MediatorLiveData<>();
        result.setValue(Resource.loading(null));

        LiveData<ApiResponse<ResultType>> apiResponse = createCall();
        result.addSource(apiResponse, response ->{
            result.removeSource(apiResponse);

            if (response.isSuccessful()){
                appExecutors.diskIO().execute(() -> {
                    ResultType result = processResponse(response);

                    saveCallResult(result);
                    appExecutors.mainThread().execute(() -> setValue(Resource.success(result)));
                });
            } else {
                setValue(Resource.error(response.errorMessage, null, response.code, response.errorBody));
            }
        });

    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }


    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected ResultType processResponse(ApiResponse<ResultType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(@Nullable ResultType item);

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<ResultType>> createCall();
}
