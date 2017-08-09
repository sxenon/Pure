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

package com.sj.pure.okgo.dispatcher;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.sxenon.pure.core.result.BaseResultDispatcher;
import com.sxenon.pure.core.result.IFetchListResultHandler;

import java.util.List;

/**
 * ResultDispatcher for list
 * Created by Sui on 2017/7/27.
 */

public abstract class BaseOkgoLResultDispatcher<R> extends BaseResultDispatcher<R> {

    public BaseOkgoLResultDispatcher(IFetchListResultHandler<R> resultHandler) {
        super(resultHandler);
    }

    /**
     * 是业务意义上的Success！
     */
    protected void handleSuccessResult(Response<List<R>> response) {
        onListDataFetched(response.body());
    }

    public abstract void onSuccess(Response<List<R>> response);

    public abstract void onCacheSuccess(Response<List<R>> response);

    public abstract void onError(Response<List<R>> response);

}
