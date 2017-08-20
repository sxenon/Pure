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

package com.sxenon.pure.core.viewholder.filler.implement.strategy;

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;
import com.sxenon.pure.core.viewholder.filler.IFillAdapterStrategy;
import com.sxenon.pure.core.viewholder.filler.IFillerViewHolder;

import java.util.List;

/**
 * PrevAndNext implement for IFillPageStrategy
 * Created by Sui on 2017/8/6.
 */

public class PrevAndNextFillPageStrategy<R> extends BaseFillPageStrategy<R> {
    private final int mInitPage;

    public PrevAndNextFillPageStrategy(int initPage) {
        super();
        mInitPage = initPage;
    }

    public PrevAndNextFillPageStrategy(IFillAdapterStrategy<R> fillAdapterStrategy, int initPage) {
        super(fillAdapterStrategy);
        mInitPage = initPage;
    }

    public void onNoMoreData() {

    }

    @Override
    public void processList(IFillerViewHolder fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        pageInfo.currentPage = pageInfo.tempPage;
        getListDataFillStrategy().onInitDataFetched(adapter, data);
    }

    @Override
    public void onFetchEmptyList(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage;
        if (pageInfo.currentPage == -1) {
            fillerViewHolder.onEmpty();
        } else {
            onNoMoreData();
        }
    }

    @Override
    public void processSingle(IFillerViewHolder fillerViewHolder, R data, IFetchSingleResultHandler<R> fetchSingleResultHandler, PageInfo pageInfo) {
        pageInfo.currentPage = pageInfo.tempPage;
        fetchSingleResultHandler.onSingleDataFetched(data);
    }

    @Override
    public void onFetchEmptySingle(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage;
        if (pageInfo.currentPage == -1) {
            fillerViewHolder.onEmpty();
        } else {
            onNoMoreData();
        }
    }

    @Override
    public void onInitialize(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        pageInfo.currentPage = -1;
        pageInfo.tempPage = mInitPage;
    }

    @Override
    public void onPullDown(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage - 1;
    }

    @Override
    public void onPullUp(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage + 1;
    }

}
