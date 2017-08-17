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

package com.sxenon.pure.core.viewholder.filler.implement;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;
import com.sxenon.pure.core.viewholder.filler.FillAdapterStrategy;
import com.sxenon.pure.core.viewholder.filler.FillPageStrategy;
import com.sxenon.pure.core.viewholder.filler.IFillerViewHolder;

/**
 * Base implement for FillPageStrategy
 * Created by Sui on 2017/8/6.
 */

public abstract class BaseFillPageStrategy<R> implements FillPageStrategy<R> {
    private final FillAdapterStrategy<R> mFillAdapterStrategy;

    public BaseFillPageStrategy() {
        this(null);
    }

    public BaseFillPageStrategy(FillAdapterStrategy<R> fillAdapterStrategy) {
        mFillAdapterStrategy = fillAdapterStrategy == null ? new DefaultFillAdapterStrategy<R>() : fillAdapterStrategy;
    }

    protected FillAdapterStrategy<R> getListDataFillStrategy() {
        return mFillAdapterStrategy;
    }

    @Override
    public void onCancel(IFillerViewHolder<R> fillerViewHolder, IPureAdapter<R> adapter, IFetchSingleResultHandler<R> fetchSingleResultHandler, PageInfo pageInfo) {
        if (fetchSingleResultHandler != null) {
            fetchSingleResultHandler.onCancel();
        }
        pageInfo.tempPage = pageInfo.currentPage;
    }

    @Override
    public void onException(IFillerViewHolder<R> fillerViewHolder, ApiException exception, IPureAdapter<R> adapter, IFetchSingleResultHandler<R> fetchSingleResultHandler, PageInfo pageInfo) {
        if (fetchSingleResultHandler != null) {
            fetchSingleResultHandler.onException(exception);
        }
        if (adapter != null) {
            adapter.clearAllItems();
        }
        pageInfo.currentPage = pageInfo.tempPage = -1;
    }
}
