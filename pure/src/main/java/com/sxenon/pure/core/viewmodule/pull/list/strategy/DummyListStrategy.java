/*
 * Copyright (c) 2018  sxenon
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

package com.sxenon.pure.core.viewmodule.pull.list.strategy;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.viewmodule.pull.IPullViewModule;
import com.sxenon.pure.core.viewmodule.pull.list.strategy.adapter.IAdapterStrategy;

import java.util.List;

/**
 * NonPull implement for IPullStrategy
 * Created by Sui on 2017/8/6.
 */

public class DummyListStrategy<R> extends BaseListStrategy<R> {

    public DummyListStrategy() {
        super();
    }

    public DummyListStrategy(IAdapterStrategy<R> adapterStrategy) {
        super(adapterStrategy);
    }

    @Override
    public void onFullList(IPullViewModule pullViewModule, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        getAdapterStrategy().onInitData(adapter, data);
    }

    @Override
    public void onPartialList(IPullViewModule pullViewModule, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        getAdapterStrategy().onInitData(adapter, data);
    }

    @Override
    public void onEmptyList(IPullViewModule pullViewModule, PageInfo pageInfo) {
        pullViewModule.onEmpty();
    }

    @Override
    public void onPullDown(PageInfo pageInfo) {

    }

    @Override
    public void onPullUp(PageInfo pageInfo) {

    }

    @Override
    public void onException(IPullViewModule pullViewModule, ApiException exception, IPureAdapter<R> adapter, PageInfo pageInfo) {

    }
}
