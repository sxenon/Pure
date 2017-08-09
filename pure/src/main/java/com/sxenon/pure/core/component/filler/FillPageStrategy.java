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

package com.sxenon.pure.core.component.filler;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

import java.util.List;

/**
 * 分页数据填充策略
 * Created by Sui on 2017/8/6.
 */

public interface FillPageStrategy<R> {
    void processListData(IFillerGroup<R> fillerGroup, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo);

    void onListDataEmpty(IFillerGroup<R> fillerGroup, PageInfo pageInfo);

    void processSingleData(IFillerGroup<R> fillerGroup, R data, IFetchSingleResultHandler<R> singleDataResultHandler, PageInfo pageInfo);

    void onSingleDataEmpty(IFillerGroup<R> fillerGroup, PageInfo pageInfo);

    void onInitialize(IFillerGroup<R> fillerGroup, PageInfo pageInfo);

    void onPullDown(IFillerGroup<R> fillerGroup, PageInfo pageInfo);

    void onPullUp(IFillerGroup<R> fillerGroup, PageInfo pageInfo);

    void onException(IFillerGroup<R> fillerGroup, ApiException exception, IPureAdapter<R> adapter, IFetchSingleResultHandler<R> singleDataResultHandler, PageInfo pageInfo);

    class PageInfo {
        public int currentPage;
        public int tempPage;

        public PageInfo(int currentPage, int tempPage) {
            this.currentPage = currentPage;
            this.tempPage = tempPage;
        }
    }
}
