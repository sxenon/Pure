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

package com.sxenon.pure.core.viewholder.filler.list;

/**
 * BaseListStrategy
 * Created by Sui on 2017/9/3.
 */

public abstract class BaseListStrategy<R> implements IFillPageStrategyForList<R> {
    private final IFillAdapterStrategy<R> mFillAdapterStrategy;

    public BaseListStrategy() {
        this(null);
    }

    public BaseListStrategy(IFillAdapterStrategy<R> fillAdapterStrategy) {
        mFillAdapterStrategy = fillAdapterStrategy == null ? new DefaultFillAdapterStrategy<R>() : fillAdapterStrategy;
    }

    public IFillAdapterStrategy<R> getListDataFillStrategy() {
        return mFillAdapterStrategy;
    }
}
