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

import android.support.annotation.NonNull;

import com.sj.pure.demo.result.http.exception.BusinessException;
import com.sj.pure.demo.result.http.exception.NetworkException;
import com.sj.pure.demo.result.http.request.EntityListRequest;
import com.sj.pure.demo.result.http.response.result.BaseNoHttpListResultDispatcher;
import com.sj.pure.demo.result.http.response.result.Result;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by Sui on 2017/8/27.
 */

public class BaseListResopnseListener<T> extends BaseResponseListener<List<T>> {
    private BaseNoHttpListResultDispatcher<T> resultDispatcher;
    private EntityListRequest<T> request;

    public BaseListResopnseListener(@NonNull BaseNoHttpListResultDispatcher<T> resultDispatcher, EntityListRequest<T> request) {
        this.resultDispatcher = resultDispatcher;
        this.request = request;
    }
    
    @Override
    public void onSucceed(int what, Response<Result<List<T>>> response) {
        // http层的请求成功，响应码可能是200、400、500。
        if (!request.isCanceled()){
            Result<List<T>> result=response.get();
            if (result.isSucceed()){
                resultDispatcher.handleSuccessResult(result.getResult());
            }else {
                BusinessException businessException=new BusinessException(result.getHeaders(),result.getError(),what);
                resultDispatcher.onApiException(businessException);
            }
        }
    }

    @Override
    public void onFailed(int what, Response<Result<List<T>>> response) {
        @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
        Exception exception = response.getException();
        NetworkException networkException=new NetworkException(exception,what);
        resultDispatcher.onApiException(networkException);
    }
}
