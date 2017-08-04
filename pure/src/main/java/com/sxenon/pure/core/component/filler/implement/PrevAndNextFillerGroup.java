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
import com.sxenon.pure.core.component.filler.FillEventWhat;
import com.sxenon.pure.core.component.filler.IPullLayout;
import com.sxenon.pure.core.component.filler.ListDataFillStrategy;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

import java.util.List;

/**
 * FillerGroup for PrevAndNext
 * Created by Sui on 2017/8/4.
 */

public class PrevAndNextFillerGroup<R, PL extends IPullLayout> extends BaseFillerGroup<R,PL> {

    private final int mInitPage;
    /**
     * Constructor
     *
     * @param pullLayout              刷新容器
     * @param singleDataResultHandler 单一数据的Handler
     */
    public PrevAndNextFillerGroup(Context context, PL pullLayout, IFetchSingleResultHandler<R> singleDataResultHandler) {
        this(context, pullLayout, null, singleDataResultHandler, 0, null);
    }

    /**
     * Constructor
     *
     * @param pullLayout              刷新容器
     * @param singleDataResultHandler 单一数据的Handler
     * @param initPage                初始为第几页
     */
    public PrevAndNextFillerGroup(Context context, PL pullLayout, IFetchSingleResultHandler<R> singleDataResultHandler, int initPage) {
        this(context, pullLayout, null, singleDataResultHandler, initPage, null);
    }

    /**
     * Constructor
     *
     * @param pullLayout 刷新容器
     * @param adapter    列表控件相关的adapter
     */
    public PrevAndNextFillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter) {
        this(context, pullLayout, adapter, null, 0, new DefaultListDataFillStrategy<R>());
    }

    /**
     * Constructor
     *
     * @param pullLayout    刷新容器
     * @param adapter       列表控件相关的adapter
     * @param initPage      初始为第几页
     */
    public PrevAndNextFillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, int initPage) {
        this(context, pullLayout, adapter, null, initPage, new DefaultListDataFillStrategy<R>());
    }

    /**
     * Constructor
     *
     * @param pullLayout           刷新容器
     * @param adapter              列表控件相关的adapter
     * @param listDataFillStrategy 列表数据填充策略
     */
    public PrevAndNextFillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, ListDataFillStrategy<R> listDataFillStrategy) {
        this(context, pullLayout, adapter, null, 0, listDataFillStrategy);
    }

    /**
     * Constructor
     *
     * @param pullLayout           刷新容器
     * @param adapter              列表控件相关的adapter
     * @param initPage             初始为第几页
     * @param listDataFillStrategy 列表数据填充策略
     */
    public PrevAndNextFillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, int initPage, ListDataFillStrategy<R> listDataFillStrategy) {
        this(context, pullLayout, adapter, null, initPage, listDataFillStrategy);
    }

    private PrevAndNextFillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, IFetchSingleResultHandler<R> singleDataResultHandler, int initPage,ListDataFillStrategy<R> listDataFillStrategy) {
        super(context, pullLayout, adapter, singleDataResultHandler, listDataFillStrategy);
        mInitPage = initPage;
    }

    /**
     * For subclass call,see demo
     */
    protected final void onBeginPullingDown() {
        if (FillEventWhat.WHAT_UNINITIALIZED==getFillEventWhat()){
            beforeInitializing();
            mCurrentPage = -1;
            mTempPage = mInitPage;
        } else {
            beforePullingDown();
            mTempPage = mCurrentPage-1;
        }
    }

    /**
     * For subclass call,see demo
     */
    protected final void onBeginPullingUp() {
        beforePullingUp();
        mTempPage = mCurrentPage + 1;
    }
    
    @Override
    protected void processListData(List<R> data) {
        mCurrentPage=mTempPage;
        getListDataFillStrategy().onInitDataFetched(getAdapter(),data);
    }

    @Override
    protected void processSingleData(R data) {
        super.processSingleData(data);
        mCurrentPage=mTempPage;
    }

    protected void onNoMoreData(){

    }

    @Override
    protected void processEmptySingleData() {
        mTempPage=mCurrentPage;
        if (mCurrentPage==-1){
            onEmpty();
        }else {
            onNoMoreData();
        }
    }

    @Override
    protected void processEmptyListData() {
        mTempPage=mCurrentPage;
        if (mCurrentPage==-1){
            onEmpty();
        }else {
            onNoMoreData();
        }
    }
}
