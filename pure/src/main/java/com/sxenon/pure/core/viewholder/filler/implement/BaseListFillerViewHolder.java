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
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.viewholder.filler.IFillPageStrategy;
import com.sxenon.pure.core.viewholder.filler.IListFillerViewHolder;
import com.sxenon.pure.core.viewholder.filler.IPullLayout;

import java.util.List;

/**
 * List data implement for FillerViewHolder
 * Created by Sui on 2017/8/19.
 */

public class BaseListFillerViewHolder<R, PL extends IPullLayout> extends BaseFillerViewHolder<R,PL> implements IListFillerViewHolder<R> {

    private IPureAdapter<R> mAdapter;

    /**
     * Constructor
     *
     * @param context          上下文
     * @param pullLayout       刷新容器
     * @param fillPageStrategy 分页数据填充策略
     */
    public BaseListFillerViewHolder(Context context, PL pullLayout, IFillPageStrategy<R> fillPageStrategy) {
        super(context, pullLayout, fillPageStrategy);
    }

    /**
     *
     * @param adapter 列表控件相关的adapter
     */
    @Override
    public void setAdapter(IPureAdapter<R> adapter){
        mAdapter=adapter;
    }

    @Override
    public void onListDataFetched(List<R> data) {
        endAllAnim();
        if (data == null || data.isEmpty()) {
            getFillPageStrategy().onFetchEmptyList(this, getPageInfo());
        } else {
            onNormal();
            getFillPageStrategy().processList(this, data, mAdapter, getPageInfo());
        }
    }

    @Override
    public void onCancel() {
        super.onCancel();
        getFillPageStrategy().onCancel(this,mAdapter, getPageInfo());
    }

    @Override
    public void onException(ApiException exception) {
        super.onException(exception);
        getFillPageStrategy().onException(this,exception,mAdapter, getPageInfo());
    }

    @Override
    protected final Object getData() {
        return mAdapter.getValues();
    }

    @Override
    protected final void restoreData(Object data) {
        //noinspection unchecked
        mAdapter.resetAllItems((List<R>) data);
    }
}
