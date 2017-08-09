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

package com.sj.pure.retrofit2.dispatcher;

import com.sxenon.pure.core.result.BaseResultDispatcher;
import com.sxenon.pure.core.result.IResultHandler;
import com.sxenon.pure.core.result.ResultHandlerType;

import retrofit2.Call;
import retrofit2.Response;

/**
 * ResultDispatcher for Retrofit2
 * Created by Sui on 2017/7/27.
 */

public abstract class Retrofit2ResultDispatcher<R> extends BaseResultDispatcher<R> {

    public Retrofit2ResultDispatcher(IResultHandler resultHandler) {
        super(resultHandler);
    }

    /**
     * 是业务意义上的Success！
     */
    protected void handleSuccessResult(Response<R> response) {
        ResultHandlerType resultHandlerType = getResultHandlerType();
        switch (resultHandlerType) {
            case FETCH_SINGLE: {
                onSingleDataFetched(response.body());
            }
            case SUBMIT: {
                onSuccess(response.body());
            }
            default: {
                throw new IllegalArgumentException("IResultHandler with wrong type");
            }
        }
    }

    public abstract void onResponse(Call<R> call, Response<R> response);

    public abstract void onFailure(Call<R> call, Throwable t);
}
