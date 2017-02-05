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

package com.sj.pure.demo.pull.bga;

import android.content.Context;

import com.sxenon.pure.core.component.filler.ListDataFillStrategy;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Sui on 2017/2/5.
 */

public class BGAAdapterViewFillerGroup<R> extends BGAFillerGroup<R> {
    public BGAAdapterViewFillerGroup(Context context, BGARefreshLayout refreshLayout, BGAAdapterViewAdapter<R> adapter) {
        super(context, refreshLayout, new BGAAdapterViewPureAdapter<>(adapter));
    }

    public BGAAdapterViewFillerGroup(Context context, BGARefreshLayout refreshLayout, BGAAdapterViewAdapter<R> adapter, boolean isFreshForAdd) {
        super(context, refreshLayout, new BGAAdapterViewPureAdapter<>(adapter), isFreshForAdd);
    }

    public BGAAdapterViewFillerGroup(Context context, BGARefreshLayout refreshLayout, BGAAdapterViewAdapter<R> adapter, ListDataFillStrategy<R> listDataFillStrategy) {
        super(context, refreshLayout, new BGAAdapterViewPureAdapter<>(adapter), listDataFillStrategy);
    }

    public BGAAdapterViewFillerGroup(Context context, BGARefreshLayout refreshLayout, BGAAdapterViewAdapter<R> adapter, boolean isFreshForAdd, ListDataFillStrategy<R> listDataFillStrategy) {
        super(context, refreshLayout, new BGAAdapterViewPureAdapter<>(adapter), isFreshForAdd, listDataFillStrategy);
    }
}
