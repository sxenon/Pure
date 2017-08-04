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

package com.sxenon.pure.core.component.filler.implement;

import android.content.Context;

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.component.filler.IPullLayout;
import com.sxenon.pure.core.component.filler.ListDataFillStrategy;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

import java.util.List;

/**
 * Process single,list data together.
 * Created by Sui on 2016/12/8.
 */

public abstract class RefreshAndMoreFillerGroup<R, PL extends IPullLayout> extends BaseFillerGroup<R,PL> {

    private final boolean mIsRefreshForAdd;
    /**
     * Constructor
     *
     * @param pullLayout              刷新容器
     * @param singleDataResultHandler 单一数据的Handler
     */
    public RefreshAndMoreFillerGroup(Context context, PL pullLayout, IFetchSingleResultHandler<R> singleDataResultHandler) {
        this(context, pullLayout, null, singleDataResultHandler, false, null);
    }

    /**
     * Constructor
     *
     * @param pullLayout 刷新容器
     * @param adapter    列表控件相关的adapter
     */
    public RefreshAndMoreFillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter) {
        this(context, pullLayout, adapter, null, false, new DefaultListDataFillStrategy<R>());
    }

    /**
     * Constructor
     *
     * @param pullLayout    刷新容器
     * @param adapter       列表控件相关的adapter
     * @param isFreshForAdd 上拉是添加最新数据还是完整更新，true 表示添加最新数据
     */
    public RefreshAndMoreFillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, boolean isFreshForAdd) {
        this(context, pullLayout, adapter, null, isFreshForAdd, new DefaultListDataFillStrategy<R>());
    }

    /**
     * Constructor
     *
     * @param pullLayout           刷新容器
     * @param adapter              列表控件相关的adapter
     * @param listDataFillStrategy 列表数据填充策略
     */
    public RefreshAndMoreFillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, ListDataFillStrategy<R> listDataFillStrategy) {
        this(context, pullLayout, adapter, null, false, listDataFillStrategy);
    }

    /**
     * Constructor
     *
     * @param pullLayout           刷新容器
     * @param adapter              列表控件相关的adapter
     * @param isFreshForAdd        上拉是添加最新数据还是完整更新，true 表示添加最新数据
     * @param listDataFillStrategy 列表数据填充策略
     */
    public RefreshAndMoreFillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, boolean isFreshForAdd, ListDataFillStrategy<R> listDataFillStrategy) {
        this(context, pullLayout, adapter, null, isFreshForAdd, listDataFillStrategy);
    }

    private RefreshAndMoreFillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, IFetchSingleResultHandler<R> singleDataResultHandler, boolean isFreshForAdd, ListDataFillStrategy<R> listDataFillStrategy) {
        super(context, pullLayout, adapter, singleDataResultHandler, listDataFillStrategy);
        mIsRefreshForAdd = isFreshForAdd;
    }

    /**
     * For subclass call,see demo
     */
    protected final void onBeginPullingDown() {
        if (mCurrentPage == -1) {
            beforeInitializing();
        } else {
            beforePullingDown();
        }
        if (getAdapter()!=null){
            if (!mIsRefreshForAdd) {
                mTempPage = 0;
            } else {
                mTempPage = mCurrentPage;
            }
        }
    }

    /**
     * For subclass call,see demo
     */
    protected final void onBeginPullingUp() {
        beforePullingUp();
        if (getAdapter()!=null){
            mTempPage = mCurrentPage + 1;
        }
    }

    protected void onNoMoreData() {

    }

    protected void onNoNewData() {

    }

    protected void onMoreDataFetched(List<R> data) {
        getListDataFillStrategy().onMoreDataFetched(getAdapter(),data);
    }

    protected void onNewDataFetched(List<R> data) {
        getListDataFillStrategy().onNewDataFetched(getAdapter(),data);
    }

    protected void onInitDataFetched(List<R> data) {
        getListDataFillStrategy().onInitDataFetched(getAdapter(),data);
    }

    @Override
    protected void processEmptySingleData() {
        onEmpty();
    }

    @Override
    protected void processSingleData(R data) {
        super.processSingleData(data);
        mCurrentPage = mTempPage = 0;
    }

    @Override
    protected void processEmptyListData() {
        if (mCurrentPage == -1) {
            onEmpty();
        } else if (mTempPage == mCurrentPage) {//refreshForAdd
            onNoNewData();
        } else {
            onNoMoreData();
        }
        mTempPage = mCurrentPage;
    }

    @Override
    protected void processListData(List<R> data) {
        if (mIsRefreshForAdd) {
            if (mCurrentPage == -1) {
                onInitDataFetched(data);
            } else if (mTempPage == mCurrentPage) {//refresh
                onNewDataFetched(data);
            } else {
                onMoreDataFetched(data);
            }
        } else {
            if (mTempPage == 0) {
                onInitDataFetched(data);
            } else {
                onMoreDataFetched(data);
            }
        }
        mCurrentPage = mTempPage;
    }
}
