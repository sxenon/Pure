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

package com.sxenon.pure.core.viewholder.filler.implement.dummy;

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.result.filler.ISingleResultFiller;
import com.sxenon.pure.core.viewholder.filler.IFillAdapterStrategy;
import com.sxenon.pure.core.viewholder.filler.IFillerViewHolder;
import com.sxenon.pure.core.viewholder.filler.implement.strategy.BaseFillPageStrategy;

import java.util.List;

/**
 * NonPull implement for IFillPageStrategy
 * Created by Sui on 2017/8/6.
 */

class DummyFillPageStrategy<R> extends BaseFillPageStrategy<R> {

    DummyFillPageStrategy() {
        super();
    }

    DummyFillPageStrategy(IFillAdapterStrategy<R> fillAdapterStrategy) {
        super(fillAdapterStrategy);
    }

    @Override
    public void processFullList(IFillerViewHolder fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        getListDataFillStrategy().onInitDataFetched(adapter, data);
    }

    @Override
    public void processPartialList(IFillerViewHolder fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        getListDataFillStrategy().onInitDataFetched(adapter, data);
    }

    @Override
    public void processEmptyList(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        fillerViewHolder.onEmpty();
    }

    @Override
    public void processSingle(IFillerViewHolder fillerViewHolder, R data, ISingleResultFiller<R> singleResultFiller, PageInfo pageInfo) {
        singleResultFiller.onSingleDataFetched(data);
    }

    @Override
    public void processEmptySingle(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        fillerViewHolder.onEmpty();
    }

    @Override
    public void onInitialize(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {

    }

    @Override
    public void onPullDown(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {

    }

    @Override
    public void onPullUp(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {

    }
}
