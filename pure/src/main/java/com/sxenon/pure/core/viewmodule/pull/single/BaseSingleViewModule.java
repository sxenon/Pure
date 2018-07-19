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

package com.sxenon.pure.core.viewmodule.pull.single;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.viewmodule.pull.BasePullViewModule;
import com.sxenon.pure.core.viewmodule.pull.IPullLayout;
import com.sxenon.pure.core.viewmodule.pull.single.strategy.ISingleStrategy;

/**
 * Single data implement for FillerViewHolder
 * Created by Sui on 2017/8/19.
 */

public abstract class BaseSingleViewModule<R, PL extends IPullLayout> extends BasePullViewModule<PL,ISingleStrategy<R>> implements ISingleViewModule<R> {
    private R mData;

    /**
     * Constructor
     *
     * @param context          上下文
     * @param pullLayout       刷新容器
     * @param singleStrategy 分页数据填充策略
     */
    public BaseSingleViewModule(Context context, PL pullLayout, ISingleStrategy<R> singleStrategy) {
        super(context, pullLayout, singleStrategy);
    }

    @Override
    public final Object getData() {
        return mData;
    }

    @Override
    public final void restoreData(Object data) {
        //noinspection unchecked
        mData = (R) data;
        onSingleResponse(mData);
    }

    @Override
    public void onSingleResponse(R response) {
        mData = response;
        endAllAnim();
        if ( response == null) {
            //onEmpty(); maybe no more,but no empty
            getPullStrategy().onEmpty(this, getPageInfo());
        } else {
            onNonEmpty();
            fillViewByData(response);
            getPullStrategy().onSingle(this, response, getPageInfo());
        }
    }

    @Override
    public void onApiException(ApiException apiException) {
        super.onApiException(apiException);
        getPullStrategy().onException(this, apiException, getPageInfo());
    }

    protected abstract void fillViewByData(@NonNull R response);
}
