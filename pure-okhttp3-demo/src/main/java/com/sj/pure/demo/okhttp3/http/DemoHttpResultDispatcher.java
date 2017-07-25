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

package com.sj.pure.demo.okhttp3.http;

import android.util.Log;

import com.sj.pure.okhttp3.BaseOkHttpResultDispatcher;
import com.sj.pure.okhttp3.convert.Convert;
import com.sj.pure.okhttp3.convert.LConvert;
import com.sxenon.pure.core.result.IResultHandler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Demo for specific app
 * Created by Sui on 2016/12/13.
 */

public abstract class DemoHttpResultDispatcher<R> extends BaseOkHttpResultDispatcher<R> {
    private static final String TAG = "Demo";

    public DemoHttpResultDispatcher(IResultHandler resultHandler, Convert<R> convert) {
        super(resultHandler, convert);
    }

    public DemoHttpResultDispatcher(IResultHandler resultHandler, LConvert<R> converter) {
        super(resultHandler, converter);
    }

    public void handleCall(Call call) {
        Log.i(TAG, call.toString());
    }

    public void handleResponse(Response response)  {
        if (response.isSuccessful()){
            try {
                handleSuccessResult(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleException(IOException exception) {
        Log.getStackTraceString(exception);
    }

}
