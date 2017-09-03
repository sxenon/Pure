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
import com.sxenon.pure.core.result.filler.ISingleResultFiller;
import com.sxenon.pure.core.viewholder.filler.IFillAdapterStrategy;
import com.sxenon.pure.core.viewholder.filler.IFillerViewHolder;

import java.util.List;

/**
 * NewAndMore implement for IFillPageStrategy
 * Created by Sui on 2017/8/6.
 */

public class NewAndMoreFillPageStrategy<R> extends BaseFillPageStrategy<R> {

    public NewAndMoreFillPageStrategy() {
        super();
    }

    public NewAndMoreFillPageStrategy(IFillAdapterStrategy<R> fillAdapterStrategy) {
        super(fillAdapterStrategy);
    }

    public void onFullMoreDataFetched(IPureAdapter<R> adapter, List<R> data) {
        getListDataFillStrategy().onMoreDataFetched(adapter, data);
    }

    public void onPartialMoreDataFetched(IPureAdapter<R> adapter, List<R> data){
        getListDataFillStrategy().onMoreDataFetched(adapter, data);
    }

    public void onNewDataFetched(IPureAdapter<R> adapter, List<R> data) {
        getListDataFillStrategy().onNewDataFetched(adapter, data);
    }

    public void onInitDataFetched(IPureAdapter<R> adapter, List<R> data) {
        getListDataFillStrategy().onInitDataFetched(adapter, data);
    }

    public void onNoMoreData() {

    }

    public void onNoNewData() {

    }

    @Override
    public void processPartialList(IFillerViewHolder fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        if (pageInfo.currentPage == -1) {
            onInitDataFetched(adapter, data);
        } else if (pageInfo.tempPage == pageInfo.currentPage) {//refresh
            onNewDataFetched(adapter, data);
        } else {
            onPartialMoreDataFetched(adapter,data);
        }
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void processFullList(IFillerViewHolder fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        if (pageInfo.currentPage == -1) {
            onInitDataFetched(adapter, data);
        } else if (pageInfo.tempPage == pageInfo.currentPage) {//refresh
            onNewDataFetched(adapter, data);
        } else {
            onFullMoreDataFetched(adapter, data);
        }
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void processEmptyList(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        if (pageInfo.currentPage == -1) {
            fillerViewHolder.onEmpty();
        } else if (pageInfo.tempPage == pageInfo.currentPage) {//refreshForAdd
            onNoNewData();
        } else {
            onNoMoreData();
        }
        pageInfo.tempPage = pageInfo.currentPage;
    }

    @Override
    public void processSingle(IFillerViewHolder fillerViewHolder, R data, ISingleResultFiller<R> singleResultFiller, PageInfo pageInfo) {
        throw new UnsupportedOperationException("Only list data support");
    }

    @Override
    public void processEmptySingle(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        throw new UnsupportedOperationException("Only list data support");
    }

    @Override
    public void onInitialize(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage;
    }

    @Override
    public void onPullDown(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage;
    }

    @Override
    public void onPullUp(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage + 1;
    }
}
