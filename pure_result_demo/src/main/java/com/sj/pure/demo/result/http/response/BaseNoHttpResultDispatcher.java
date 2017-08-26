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

package com.sj.pure.demo.result.http.response;

import android.util.Log;

import com.sj.pure.demo.result.http.exception.BusinessException;
import com.sj.pure.demo.result.http.exception.NetworkException;
import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.result.BaseResultDispatcher;
import com.sxenon.pure.core.result.ResultHandlerType;
import com.sxenon.pure.core.result.handler.IResultHandler;
import com.yanzhenjie.nohttp.error.TimeoutError;

/**
 * ResultDispatcher fo NoHttp
 * Created by Sui on 2017/8/27.
 */

public class BaseNoHttpResultDispatcher<T> extends BaseResultDispatcher<T>  {
    private static final String TAG="ResultDispatcher";

    public BaseNoHttpResultDispatcher(IResultHandler resultHandler) {
        super(resultHandler);
    }

    /**
     * 是业务意义上的Success！
     */
    public void handleSuccessResult(T result) {
        ResultHandlerType resultHandlerType = getResultHandlerType();
        switch (resultHandlerType) {
            case FETCH_SINGLE: {
                onSingleDataFetched(result);
            }
            break;
            case FETCH_LIST:{
                onListDataFetched(result);
            }
            break;
            case SUBMIT: {
                onSubmitSuccess(result);
            }
            break;
            default: {
                throw new IllegalArgumentException("IResultHandler with wrong type");
            }
        }
    }

    @Override
    public void onApiException(ApiException apiException) {
        if (apiException instanceof NetworkException){
            Exception exception=((NetworkException) apiException).getException();
            Log.e(TAG,exception.getMessage());
            if (exception instanceof TimeoutError){
                Log.e(TAG,"Timeout");
            }
        }else if (apiException instanceof BusinessException){
            Log.w(TAG,((BusinessException) apiException).getError());
        }
        super.onApiException(apiException);
    }
}
