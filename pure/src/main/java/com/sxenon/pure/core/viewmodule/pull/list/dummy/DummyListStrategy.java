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

package com.sxenon.pure.core.viewmodule.pull.list.dummy;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.viewmodule.pull.IPullViewModule;
import com.sxenon.pure.core.viewmodule.pull.list.BaseListStrategy;
import com.sxenon.pure.core.viewmodule.pull.list.IAdapterStrategy;

import java.util.List;

/**
 * NonPull implement for IPullStrategy
 * Created by Sui on 2017/8/6.
 */

public class DummyListStrategy<R> extends BaseListStrategy<R> {

    public DummyListStrategy() {
        super();
    }

    DummyListStrategy(IAdapterStrategy<R> fillAdapterStrategy) {
        super(fillAdapterStrategy);
    }

    @Override
    public void processFullList(IPullViewModule fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        getListDataFillStrategy().onInitDataFetched(adapter, data);
    }

    @Override
    public void processPartialList(IPullViewModule fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        getListDataFillStrategy().onInitDataFetched(adapter, data);
    }

    @Override
    public void processEmptyList(IPullViewModule fillerViewHolder, PageInfo pageInfo) {
        fillerViewHolder.onEmpty();
    }

    @Override
    public void onPullDown(PageInfo pageInfo) {

    }

    @Override
    public void onPullUp(PageInfo pageInfo) {

    }

    @Override
    public void onException(IPullViewModule fillerViewHolder, ApiException exception, IPureAdapter<R> adapter, PageInfo pageInfo) {

    }
}