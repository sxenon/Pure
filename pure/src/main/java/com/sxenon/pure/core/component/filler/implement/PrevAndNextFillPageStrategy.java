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

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.component.filler.FillAdapterStrategy;
import com.sxenon.pure.core.component.filler.IFillerGroup;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

import java.util.List;

/**
 * PrevAndNext implement for FillPageStrategy
 * Created by Sui on 2017/8/6.
 */

public class PrevAndNextFillPageStrategy<R> extends BaseFillPageStrategy<R> {
    private final int mInitPage;

    public PrevAndNextFillPageStrategy(int initPage){
        super();
        mInitPage=initPage;
    }

    public PrevAndNextFillPageStrategy(FillAdapterStrategy<R> fillAdapterStrategy,int initPage){
        super(fillAdapterStrategy);
        mInitPage=initPage;
    }

    public void onNoMoreData(){

    }

    @Override
    public void processListData(IFillerGroup<R> fillerGroup, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        pageInfo.currentPage=pageInfo.tempPage;
        getListDataFillStrategy().onInitDataFetched(adapter,data);
    }

    @Override
    public void onListDataEmpty(IFillerGroup<R> fillerGroup, PageInfo pageInfo) {
        pageInfo.tempPage=pageInfo.currentPage;
        if (pageInfo.currentPage==-1){
            fillerGroup.onEmpty();
        }else {
            onNoMoreData();
        }
    }

    @Override
    public void processSingleData(IFillerGroup<R> fillerGroup, R data, IFetchSingleResultHandler<R> singleDataResultHandler, PageInfo pageInfo) {
        pageInfo.currentPage=pageInfo.tempPage;
        singleDataResultHandler.onSingleDataFetched(data);
    }

    @Override
    public void onSingleDataEmpty(IFillerGroup<R> fillerGroup, PageInfo pageInfo) {
        pageInfo.tempPage=pageInfo.currentPage;
        if (pageInfo.currentPage==-1){
            fillerGroup.onEmpty();
        }else {
            onNoMoreData();
        }
    }

    @Override
    public void onInitialize(IFillerGroup<R> fillerGroup, PageInfo pageInfo) {
        pageInfo.currentPage=-1;
        pageInfo.tempPage = mInitPage;
    }

    @Override
    public void onPullDown(IFillerGroup<R> fillerGroup, PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage-1;
    }

    @Override
    public void onPullUp(IFillerGroup<R> fillerGroup, PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage+1;
    }

    @Override
    public void onException(IFillerGroup<R> fillerGroup, ApiException exception, IPureAdapter<R> adapter, IFetchSingleResultHandler<R> singleDataResultHandler, PageInfo pageInfo) {
        if (singleDataResultHandler != null) {
            singleDataResultHandler.onException(exception);
        }
        if (adapter != null) {
            adapter.clearAllItems();
        }
        pageInfo.currentPage = pageInfo.tempPage = -1;
    }
}
