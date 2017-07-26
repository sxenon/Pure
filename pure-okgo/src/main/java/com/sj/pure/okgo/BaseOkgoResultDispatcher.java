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

package com.sj.pure.okgo;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.sxenon.pure.core.result.BaseResultDispatcher;
import com.sxenon.pure.core.result.IResultHandler;
import com.sxenon.pure.core.result.ResultHandlerType;

/**
 * BaseOkgoResultDispatcher
 * Created by Sui on 2017/7/26.
 */

public abstract class BaseOkgoResultDispatcher<R> extends BaseResultDispatcher<R> {
    public BaseOkgoResultDispatcher(IResultHandler resultHandler) {
        super(resultHandler);
    }

    public void onStart(Request<R, ? extends Request> request) {

    }

    public void onSuccess(Response<R> response) {

    }

    public void onCacheSuccess(Response<R> response) {

    }

    public void onError(Response<R> response) {

    }

    public void onFinish() {

    }

    public void uploadProgress(Progress progress) {

    }

    public void downloadProgress(Progress progress) {

    }

    protected void handleSuccessResult(Response<R> response){
        ResultHandlerType resultHandlerType=getResultHandlerType();
        switch (resultHandlerType){
            case FETCH_SINGLE:{
                R result=response.body();
                onSingleDataFetched(response.body());
            }
            case SUBMIT: {
                onSuccess(response.body());
            }
            default:{
                throw new IllegalArgumentException("IResultHandler with wrong type");
            }
        }
    }
}
