/*
 * Copyright (c) 2017 sxenon
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

package com.sj.pure.demo.retrofit2;

import android.util.Log;

import com.sxenon.pure.core.result.BaseResultDispatcher;
import com.sxenon.pure.core.result.IResultHandler;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Demo for RetrofitResultDispatcher
 * Created by Sui on 2016/12/15.
 */

public abstract class DemoRetrofitResultDispatcher<T> extends BaseResultDispatcher {
    private static final String TAG = "Demo";

    public DemoRetrofitResultDispatcher(IResultHandler resultHandler) {
        super(resultHandler);
    }

    public void handleCall(Call<T> call) {
        Log.i(TAG, call.toString());
    }

    public void handleResponse(Response<T> response) {
        Log.i(TAG, response.body()+"");
    }

    public void handleException(Throwable t) {
        Log.getStackTraceString(t);
    }
}
