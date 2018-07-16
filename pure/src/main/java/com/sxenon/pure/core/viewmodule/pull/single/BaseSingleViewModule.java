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

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.result.handler.ISingleResultHandler;
import com.sxenon.pure.core.viewmodule.pull.BasePullViewModule;
import com.sxenon.pure.core.viewmodule.pull.IPullLayout;

/**
 * Single data implement for FillerViewHolder
 * Created by Sui on 2017/8/19.
 */

public class BaseSingleViewModule<R, PL extends IPullLayout> extends BasePullViewModule<PL,ISingleStrategy<R>> implements ISingleViewModule<R> {
    private ISingleResultHandler<R> mSingleResultFiller;
    private R mData;

    /**
     * Constructor
     *
     * @param context          上下文
     * @param pullLayout       刷新容器
     * @param fillPageStrategy 分页数据填充策略
     */
    public BaseSingleViewModule(Context context, PL pullLayout, ISingleStrategy<R> fillPageStrategy) {
        super(context, pullLayout, fillPageStrategy);
    }

    /**
     * @param singleResultHandler 单一数据的Filler
     */
    @Override
    public void setSingleResultHandler(ISingleResultHandler<R> singleResultHandler) {
        mSingleResultFiller = singleResultHandler;
    }

    @Override
    public final Object getData() {
        return mData;
    }

    @Override
    public final void restoreData(Object data) {
        //noinspection unchecked
        mData = (R) data;
        mSingleResultFiller.onSingleData(mData);
    }

    @Override
    public void onSingleData(R data) {
        mData = data;
        endAllAnim();
        if (data == null) {
            getPullStrategy().processEmptySingle(this, getPageInfo());
        } else {
            onNonEmpty();
            getPullStrategy().processSingle(this, data, mSingleResultFiller, getPageInfo());
        }
    }

    @Override
    public void onApiException(ApiException apiException) {
        super.onApiException(apiException);
        getPullStrategy().onException(this, apiException, mSingleResultFiller, getPageInfo());
    }
}
