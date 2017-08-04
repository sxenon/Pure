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

package com.sxenon.pure.core.component.filler.implement;

import android.content.Context;

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.component.filler.IPullLayout;
import com.sxenon.pure.core.component.filler.ListDataFillStrategy;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

import java.util.List;

/**
 * FillerGroup for PrevAndNext
 * Created by Sui on 2017/8/4.
 */

public class PrevAndNextFillerGroup<R, PL extends IPullLayout> extends BaseFillerGroup<R,PL> {

    protected PrevAndNextFillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, IFetchSingleResultHandler<R> singleDataResultHandler, ListDataFillStrategy<R> listDataFillStrategy) {
        super(context, pullLayout, adapter, singleDataResultHandler, listDataFillStrategy);
    }

    @Override
    protected void processListData(List<R> data) {

    }

    @Override
    protected void processSingleData(R data) {
        super.processSingleData(data);
    }

    @Override
    protected void processEmptySingleData() {

    }

    @Override
    protected void processEmptyListData() {

    }
}
