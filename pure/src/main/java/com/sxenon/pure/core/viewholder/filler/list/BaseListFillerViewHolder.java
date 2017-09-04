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

package com.sxenon.pure.core.viewholder.filler.list;

import android.content.Context;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.viewholder.filler.BaseFillerViewHolder;
import com.sxenon.pure.core.viewholder.filler.IPullLayout;

import java.util.List;

/**
 * List data implement for FillerViewHolder
 * Created by Sui on 2017/8/19.
 */

public class BaseListFillerViewHolder<R, PL extends IPullLayout,S extends IFillPageStrategyForList<R>> extends BaseFillerViewHolder<PL,S> implements IListFillerViewHolder<R> {

    private IPureAdapter<R> mAdapter;

    private int mDataSizeInFullPage;

    /**
     * Constructor
     *
     * @param context          上下文
     * @param pullLayout       刷新容器
     * @param fillPageStrategy 分页数据填充策略
     * @param dataSizeInFullPage 完整页数据个数
     */
    public BaseListFillerViewHolder(Context context, PL pullLayout, S fillPageStrategy, int dataSizeInFullPage) {
        super(context, pullLayout, fillPageStrategy);
        mDataSizeInFullPage = dataSizeInFullPage;
    }

    /**
     * @param adapter 列表控件相关的adapter
     */
    @Override
    public void setAdapter(IPureAdapter<R> adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onListDataFetched(List<R> data) {
        endAllAnim();
        if (data == null || data.isEmpty()) {
            getFillPageStrategy().processEmptyList(this, getPageInfo());
        } else {
            onNonEmpty();
            if (data.size()<mDataSizeInFullPage){
                getFillPageStrategy().processPartialList(this,data,mAdapter,getPageInfo());
            }else {
                getFillPageStrategy().processFullList(this, data, mAdapter, getPageInfo());
            }
        }
    }

    @Override
    public void onApiException(ApiException apiException) {
        super.onApiException(apiException);
        getFillPageStrategy().onException(this, apiException, mAdapter, getPageInfo());
    }

    @Override
    public final Object getData() {
        return mAdapter.getValues();
    }

    @Override
    public final void restoreData(Object data) {
        //noinspection unchecked
        mAdapter.resetAllItems((List<R>) data);
    }
}
