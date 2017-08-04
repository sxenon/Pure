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
import android.view.View;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.component.filler.FillEventWhat;
import com.sxenon.pure.core.component.filler.IPullLayout;
import com.sxenon.pure.core.component.filler.ListDataFillStrategy;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;
import com.sxenon.pure.core.util.CommonUtils;
import com.sxenon.pure.core.util.Preconditions;

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
        if (mCurrentPageCount == 0) {
            beforeInitializing();
        } else {
            beforePullingDown();
        }
        if (getAdapter()!=null){
            if (!mIsRefreshForAdd) {
                tempPageCount = 1;
            } else {
                tempPageCount = mCurrentPageCount;
            }
        }
    }

    /**
     * For subclass call,see demo
     */
    protected final void onBeginPullingUp() {
        beforePullingUp();
        if (getAdapter()!=null){
            tempPageCount = mCurrentPageCount + 1;
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
    public void onEmpty() {
        setFillerEventWhat(FillEventWhat.WHAT_EMPTY);
        CommonUtils.setViewVisibility(getExceptionView(), View.GONE);
        CommonUtils.setViewVisibility(getEmptyView(), View.VISIBLE);
    }

    @Override
    public void onSingleDataFetched(R data) {
        setValue(data);
        Preconditions.checkNotNull(getSingleDataResultHandler(), "single data but no singleDataResult!");
        endAllAnim();
        if (data == null) {
            onEmpty();
        } else {
            setFillerEventWhat(FillEventWhat.WHAT_NORMAL);
            mCurrentPageCount = tempPageCount = 1;
            CommonUtils.setViewVisibility(getEmptyView(), View.GONE);
            CommonUtils.setViewVisibility(getExceptionView(), View.GONE);
            getSingleDataResultHandler().onSingleDataFetched(data);
        }
    }

    @Override
    public void onListDataFetched(List<R> data) {
        setFillerEventWhat(FillEventWhat.WHAT_NORMAL);
        CommonUtils.setViewVisibility(getEmptyView(), View.GONE);
        CommonUtils.setViewVisibility(getExceptionView(), View.GONE);
        Preconditions.checkNotNull(getAdapter(), "list data but no adapter!");
        endAllAnim();
        if (data == null || data.isEmpty()) {
            if (mCurrentPageCount == 0) {
                onEmpty();
            } else if (tempPageCount == mCurrentPageCount) {//refreshForAdd
                onNoNewData();
            } else {
                onNoMoreData();
            }
            tempPageCount = mCurrentPageCount;
        } else {
            if (mIsRefreshForAdd) {
                if (mCurrentPageCount == 0) {
                    onInitDataFetched(data);
                } else if (tempPageCount == mCurrentPageCount) {//refresh
                    onNewDataFetched(data);
                } else {
                    onMoreDataFetched(data);
                }
            } else {
                if (tempPageCount == 1) {
                    onInitDataFetched(data);
                } else {
                    onMoreDataFetched(data);
                }
            }
            mCurrentPageCount = tempPageCount;
        }
    }

    @Override
    public void onCancel() {
        endAllAnim();
        if (getSingleDataResultHandler() != null) {
            getSingleDataResultHandler().onCancel();
        }
        mCurrentPageCount = tempPageCount;
    }

    @Override
    public void onException(ApiException exception) {
        endAllAnim();
        setFillerEventWhat(FillEventWhat.WHAT_EXCEPTION);
        setException(exception);
        resetPageCount();
        CommonUtils.setViewVisibility(getEmptyView(), View.GONE);
        CommonUtils.setViewVisibility(getExceptionView(), View.VISIBLE);
        if (getSingleDataResultHandler() != null) {
            getSingleDataResultHandler().onException(exception);
        }
    }
    
    //Getter start

    //Getter end
}
