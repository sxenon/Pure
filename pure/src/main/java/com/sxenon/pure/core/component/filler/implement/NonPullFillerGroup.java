/*
 * Copyright (c) 2017 sxenon
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
import com.sxenon.pure.core.component.filler.ListDataFillStrategy;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

import java.util.List;

/**
 * UnSupport pull!
 * Created by Sui on 2016/12/12.
 */

public abstract class NonPullFillerGroup<R> extends BaseFillerGroup<R, NonPullLayout> {
    public NonPullFillerGroup(Context context, IFetchSingleResultHandler<R> singleDataResult) {
        super(context, new NonPullLayout(), null,singleDataResult,null);
    }

    public NonPullFillerGroup(Context context, IPureAdapter<R> adapter) {
        this(context,adapter, new DefaultListDataFillStrategy<R>());
    }
    
    public NonPullFillerGroup(Context context, IPureAdapter<R> adapter, ListDataFillStrategy<R> listDataFillStrategy){
        super(context,new NonPullLayout(),adapter,null,listDataFillStrategy);
    }

    @Override
    public void onSingleDataFetched(R data) {
        getSingleDataResultHandler().onSingleDataFetched(data);
    }

    @Override
    public void onListDataFetched(List<R> data) {
        getListDataFillStrategy().onInitDataFetched(getAdapter(),data);
    }

    @Override
    public void onCancel() {
        endAllAnim();
        if (getSingleDataResultHandler() != null) {
            getSingleDataResultHandler().onCancel();
        }
    }
}
