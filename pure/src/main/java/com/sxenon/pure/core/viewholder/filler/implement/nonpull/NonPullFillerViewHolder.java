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

package com.sxenon.pure.core.viewholder.filler.implement.nonpull;

import android.content.Context;

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;
import com.sxenon.pure.core.viewholder.filler.implement.BaseFillerViewHolder;

/**
 * UnSupport pull!
 * Created by Sui on 2016/12/12.
 */

public class NonPullFillerViewHolder<R> extends BaseFillerViewHolder<R, NonPullLayout> {
    public NonPullFillerViewHolder(Context context, IFetchSingleResultHandler<R> fetchSingleResultHandler) {
        super(context, new NonPullLayout(), fetchSingleResultHandler, new NonPullFillPageStrategy<R>());
    }

    public NonPullFillerViewHolder(Context context, IPureAdapter<R> adapter) {
        super(context, new NonPullLayout(), adapter, new NonPullFillPageStrategy<R>());
    }

}
