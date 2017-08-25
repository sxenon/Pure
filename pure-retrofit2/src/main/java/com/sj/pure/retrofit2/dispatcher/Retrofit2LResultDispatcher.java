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
import com.sxenon.pure.core.result.handler.IFetchListResultHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * ResultDispatcher for Retrofit2
 * Created by Sui on 2017/7/27.
 */

public abstract class Retrofit2LResultDispatcher<R> extends BaseResultDispatcher<R> {

    public Retrofit2LResultDispatcher(IFetchListResultHandler<R> resultHandler) {
        super(resultHandler);
    }

    /**
     * 是业务意义上的Success！
     */
    protected void handleSuccessResult(Response<List<R>> response) {
        onListDataFetched(response.body());
    }

    public abstract void onResponse(Call<List<R>> call, Response<List<R>> response);

    public abstract void onFailure(Call<List<R>> call, Throwable t);
}
