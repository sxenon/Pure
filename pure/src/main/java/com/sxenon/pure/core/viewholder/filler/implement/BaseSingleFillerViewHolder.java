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

package com.sxenon.pure.core.viewholder.filler.implement;

import android.content.Context;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.result.filler.ISingleResultFiller;
import com.sxenon.pure.core.viewholder.filler.IFillPageStrategy;
import com.sxenon.pure.core.viewholder.filler.IPullLayout;
import com.sxenon.pure.core.viewholder.filler.ISingleFillerViewHolder;

/**
 * Single data implement for FillerViewHolder
 * Created by Sui on 2017/8/19.
 */

public class BaseSingleFillerViewHolder<R, PL extends IPullLayout> extends BaseFillerViewHolder<R,PL> implements ISingleFillerViewHolder<R> {
    private ISingleResultFiller<R> mSingleResultFiller;
    private R mData;
    /**
     * Constructor
     *
     * @param context          上下文
     * @param pullLayout       刷新容器
     * @param fillPageStrategy 分页数据填充策略
     */
    public BaseSingleFillerViewHolder(Context context, PL pullLayout, IFillPageStrategy<R> fillPageStrategy) {
        super(context, pullLayout, fillPageStrategy);
    }

    /**
     *
     * @param singleResultFiller 单一数据的Filler
     */
    @Override
    public void setSingleResultFiller(ISingleResultFiller<R> singleResultFiller){
        mSingleResultFiller =singleResultFiller;
    }

    @Override
    public final Object getData() {
        return mData;
    }

    @Override
    public final void restoreData(Object data) {
        //noinspection unchecked
        mData= (R) data;
        mSingleResultFiller.onSingleDataFetched(mData);
    }

    @Override
    public void onSingleDataFetched(R data) {
        mData=data;
        endAllAnim();
        if (data == null) {
            getFillPageStrategy().onFetchEmptySingle(this, getPageInfo());
        } else {
            onNormal();
            getFillPageStrategy().processSingle(this, data, mSingleResultFiller, getPageInfo());
        }
    }

    @Override
    public void onCancel() {
        super.onCancel();
        getFillPageStrategy().onCancel(this, mSingleResultFiller, getPageInfo());
    }

    @Override
    public void onApiException(ApiException apiException) {
        super.onApiException(apiException);
        getFillPageStrategy().onException(this, apiException, mSingleResultFiller, getPageInfo());
    }
}
