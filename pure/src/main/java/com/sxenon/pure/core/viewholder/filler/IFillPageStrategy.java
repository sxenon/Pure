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

package com.sxenon.pure.core.viewholder.filler;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

import java.util.List;

/**
 * 分页数据填充策略
 * Created by Sui on 2017/8/6.
 */

public interface IFillPageStrategy<R> {
    void processListData(IFillerViewHolder fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo);

    void onFetchEmptyListData(IFillerViewHolder fillerViewHolder, PageInfo pageInfo);

    void processSingleData(IFillerViewHolder fillerViewHolder, R data, IFetchSingleResultHandler<R> fetchSingleResultHandler, PageInfo pageInfo);

    void onFetchEmptySingleData(IFillerViewHolder fillerViewHolder, PageInfo pageInfo);

    void onInitialize(IFillerViewHolder fillerViewHolder, PageInfo pageInfo);

    void onPullDown(IFillerViewHolder fillerViewHolder, PageInfo pageInfo);

    void onPullUp(IFillerViewHolder fillerViewHolder, PageInfo pageInfo);

    void onException(IFillerViewHolder fillerViewHolder, ApiException exception, IPureAdapter<R> adapter,PageInfo pageInfo);

    void onException(IFillerViewHolder fillerViewHolder, ApiException exception, IFetchSingleResultHandler<R> fetchSingleResultHandler, PageInfo pageInfo);

    void onCancel(IFillerViewHolder fillerViewHolder, IPureAdapter<R> adapter, PageInfo pageInfo);

    void onCancel(IFillerViewHolder fillerViewHolder, IFetchSingleResultHandler<R> fetchSingleResultHandler, PageInfo pageInfo);

    class PageInfo {
        public int currentPage;
        public int tempPage;

        public PageInfo(int currentPage, int tempPage) {
            this.currentPage = currentPage;
            this.tempPage = tempPage;
        }
    }
}
