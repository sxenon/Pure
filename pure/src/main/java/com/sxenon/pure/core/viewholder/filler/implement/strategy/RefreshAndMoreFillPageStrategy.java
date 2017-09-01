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
 * RefreshAndMoreFillPageStrategy
 * Created by Sui on 2017/8/6.
 */

public class RefreshAndMoreFillPageStrategy<R> extends BaseFillPageStrategy<R> {

    public RefreshAndMoreFillPageStrategy() {
        super();
    }

    public RefreshAndMoreFillPageStrategy(IFillAdapterStrategy<R> fillAdapterStrategy) {
        super(fillAdapterStrategy);
    }

    public void onFullMoreDataFetched(IPureAdapter<R> adapter, List<R> data) {
        getListDataFillStrategy().onMoreDataFetched(adapter, data);
    }

    public void onPartialMoreDataFetched(IPureAdapter<R> adapter, List<R> data){
        getListDataFillStrategy().onMoreDataFetched(adapter, data);
    }

    public void onInitDataFetched(IPureAdapter<R> adapter, List<R> data) {
        getListDataFillStrategy().onInitDataFetched(adapter, data);
    }

    public void onNoMoreData() {

    }

    @Override
    public void onInitialize(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        pageInfo.tempPage = 0;
    }

    @Override
    public void onPullDown(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        pageInfo.tempPage = 0;
    }

    @Override
    public void onPullUp(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage + 1;
    }

    @Override
    public void processList(IFillerViewHolder fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        if (pageInfo.tempPage == 0) {
            onInitDataFetched(adapter, data);
        } else {
            if (data.size()< getListSizeInFullPage()){
                onPartialMoreDataFetched(adapter,data);
            }else {
                onFullMoreDataFetched(adapter, data);
            }
        }
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void onFetchEmptyList(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        if (pageInfo.currentPage == -1) {
            fillerViewHolder.onEmpty();
        } else {
            onNoMoreData();
        }
        pageInfo.tempPage = pageInfo.currentPage;
    }

    @Override
    public void processSingle(IFillerViewHolder fillerViewHolder, R data, ISingleResultFiller<R> singleResultFiller, PageInfo pageInfo) {
        singleResultFiller.onSingleDataFetched(data);
        pageInfo.tempPage = pageInfo.currentPage = 0;
    }

    @Override
    public void onFetchEmptySingle(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        fillerViewHolder.onEmpty();
        pageInfo.tempPage = pageInfo.currentPage =-1;
    }
}
