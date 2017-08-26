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
package com.sj.pure.demo.result.http;

import android.support.annotation.NonNull;

import com.sj.pure.demo.result.http.exception.BusinessException;
import com.sj.pure.demo.result.http.exception.NetworkException;
import com.sj.pure.demo.result.http.result.AbsNoHttpSingleResultDispatcher;
import com.sxenon.pure.core.ApiException;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by Yan Zhenjie on 2016/12/17.
 */
public class DefaultResponseListener<T> implements OnResponseListener<Result<T>> {

    private AbsNoHttpSingleResultDispatcher<T> resultDispatcher;
    private AbstractRequest<T> abstractRequest;

    public DefaultResponseListener(@NonNull AbsNoHttpSingleResultDispatcher<T> resultDispatcher, AbstractRequest<T> abstractRequest) {
        this.resultDispatcher = resultDispatcher;
        this.abstractRequest = abstractRequest;
    }

    @Override
    public void onStart(int what) {
        resultDispatcher.onStart(what);
    }

    @Override
    public void onSucceed(int what, Response<Result<T>> response) {
        // http层的请求成功，响应码可能是200、400、500。
        if (!abstractRequest.isCanceled()){
            Result<T> result=response.get();
            if (result.isSucceed()){
                resultDispatcher.handleSuccessResult(result.getResult());
            }else {
                BusinessException businessException=new BusinessException(result.getHeaders(),result.getError(),what);
                resultDispatcher.onException(businessException);
            }
        }
    }

    @Override
    public void onFailed(int what, Response<Result<T>> response) {
        @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
        Exception exception = response.getException();
        NetworkException networkException=new NetworkException(exception,what);
        resultDispatcher.onException(networkException);
//        ApiException apiException=new ApiException(exception.getClass().getSimpleName(),exception.getMessage());
//        resultDispatcher.onException(apiException);
//        if (exception instanceof TimeoutError) { // 超时。
//            // Toast
//
//        }
//        if (!abstractRequest.isCanceled()){
//            resultDispatcher.onFailed(what);
//        }
    }

    @Override
    public void onFinish(int what) {
        resultDispatcher.onFinish(what);
    }
}
