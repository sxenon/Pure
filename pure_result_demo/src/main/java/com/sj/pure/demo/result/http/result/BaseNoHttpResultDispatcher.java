/*
 * Copyright (c) 2017  sxenon
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

package com.sj.pure.demo.result.http.result;

import android.util.Log;

import com.sj.pure.demo.result.http.exception.BusinessException;
import com.sj.pure.demo.result.http.exception.NetworkException;
import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.result.BaseResultDispatcher;
import com.sxenon.pure.core.result.handler.IResultHandler;

/**
 * Created by Sui on 2017/8/27.
 */

public class BaseNoHttpResultDispatcher<T> extends BaseResultDispatcher<T>  {
    private static final String TAG="ResultDispatcher";

    public BaseNoHttpResultDispatcher(IResultHandler resultHandler) {
        super(resultHandler);
    }

    public void onStart(int what){

    }

    public void onFailed(int what) {

    }

    public void onFinish(int what) {

    }

    @Override
    public void onException(ApiException exception) {
        if (exception instanceof NetworkException){
            Log.d(TAG,((NetworkException) exception).getException().getMessage());
        }else if (exception instanceof BusinessException){
            Log.w(TAG,((BusinessException) exception).getError());
        }
        super.onException(exception);
    }
}
