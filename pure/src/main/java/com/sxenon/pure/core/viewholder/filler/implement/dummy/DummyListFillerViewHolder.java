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

package com.sxenon.pure.core.viewholder.filler.implement.dummy;

import android.content.Context;

import com.sxenon.pure.core.viewholder.filler.IFillAdapterStrategy;
import com.sxenon.pure.core.viewholder.filler.implement.BaseListFillerViewHolder;

/**
 * UnSupport pull!
 * Created by Sui on 2016/12/12.
 */

public class DummyListFillerViewHolder<R> extends BaseListFillerViewHolder<R, DummyPullLayout> {
    public DummyListFillerViewHolder(Context context) {
        super(context, new DummyPullLayout(), new DummyFillPageStrategy<R>());
    }

    public DummyListFillerViewHolder(Context context, IFillAdapterStrategy<R> fillAdapterStrategy) {
        super(context, new DummyPullLayout(), new DummyFillPageStrategy<>(fillAdapterStrategy));
    }
}