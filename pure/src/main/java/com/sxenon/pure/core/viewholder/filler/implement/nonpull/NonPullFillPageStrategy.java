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

package com.sxenon.pure.core.viewholder.filler.implement.nonpull;

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;
import com.sxenon.pure.core.viewholder.filler.FillAdapterStrategy;
import com.sxenon.pure.core.viewholder.filler.IFillerViewHolder;
import com.sxenon.pure.core.viewholder.filler.implement.BaseFillPageStrategy;

import java.util.List;

/**
 * NonPull implement for FillPageStrategy
 * Created by Sui on 2017/8/6.
 */

class NonPullFillPageStrategy<R> extends BaseFillPageStrategy<R> {

    NonPullFillPageStrategy() {
        super();
    }

    NonPullFillPageStrategy(FillAdapterStrategy<R> fillAdapterStrategy) {
        super(fillAdapterStrategy);
    }

    @Override
    public void processListData(IFillerViewHolder<R> fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        getListDataFillStrategy().onInitDataFetched(adapter, data);
    }

    @Override
    public void onFetchEmptyListData(IFillerViewHolder<R> fillerViewHolder, PageInfo pageInfo) {
        fillerViewHolder.onEmpty();
    }

    @Override
    public void processSingleData(IFillerViewHolder<R> fillerViewHolder, R data, IFetchSingleResultHandler<R> fetchSingleResultHandler, PageInfo pageInfo) {
        fetchSingleResultHandler.onSingleDataFetched(data);
    }

    @Override
    public void onFetchEmptySingleData(IFillerViewHolder<R> fillerViewHolder, PageInfo pageInfo) {
        fillerViewHolder.onEmpty();
    }

    @Override
    public void onInitialize(IFillerViewHolder<R> fillerViewHolder, PageInfo pageInfo) {

    }

    @Override
    public void onPullDown(IFillerViewHolder<R> fillerViewHolder, PageInfo pageInfo) {

    }

    @Override
    public void onPullUp(IFillerViewHolder<R> fillerViewHolder, PageInfo pageInfo) {

    }
}
