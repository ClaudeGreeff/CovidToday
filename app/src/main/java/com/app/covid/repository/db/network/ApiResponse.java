/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.covid.repository.db.network;

import androidx.annotation.Nullable;

import com.app.covid.repository.db.model.Collection;
import com.app.covid.repository.db.model.ErrorBody;
import com.google.gson.Gson;

import retrofit2.Response;
import timber.log.Timber;

/**
 * Common class used by API responses.
 * @param <T>
 */
public class ApiResponse<T> {
    public final int code;
    @Nullable
    public final T body;
    @Nullable
    public final String errorMessage;
    @Nullable
    public ErrorBody errorBody;
    public Boolean dataLimited;

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
        errorBody = null;
        dataLimited = false;
    }

    public ApiResponse(@Nullable String message) {
        code = 500;
        errorMessage = message;
        body = null;
        errorBody = null;
        dataLimited = false;
    }

    public ApiResponse(Response<T> response) {
        code = response.code();
        Timber.i(response.toString());
        if(response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
            errorBody = null;
            if(body instanceof Collection){
                dataLimited = ((Collection) body).getDataLimited();
            }else{
                dataLimited = false;
            }
        } else {
            if (response.errorBody() != null) {
                Gson gson = new Gson();
                try {
                    errorBody = gson.fromJson(response.errorBody().string(), ErrorBody.class);
                } catch (Exception e) {
                    Timber.e(e);
                    errorBody = null;
                }
                errorMessage = handleError();
                body = null;
            } else {
                errorMessage = handleError();
                body = null;
                errorBody = null;
            }
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    private String handleError() {
        if (code  < 0) {
            //Special error don't present - example: request cancelled (-999)
            return "";
        } else if (code >= 500) {
            return GENERIC_ERROR_500;
        } else if (code >= 400) {
            return GENERIC_ERROR_400 ;
        } else {
            return GENERIC_ERROR_OTHER;
        }
    }

    public static String GENERIC_ERROR_500 = "Service unavailable, please try again later.";
    public static String GENERIC_ERROR_400 = "Service unavailable, please try again later.";
    public static String GENERIC_ERROR_OTHER = "Network error, please try again later.";
}
