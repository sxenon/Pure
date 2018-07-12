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

package com.sxenon.pure.core.viewmodule.filler.list.dummy;

import android.content.Context;

import com.sxenon.pure.core.viewmodule.filler.DummyPullLayout;
import com.sxenon.pure.core.viewmodule.filler.list.BaseListViewModule;
import com.sxenon.pure.core.viewmodule.filler.list.IAdapterStrategy;

/**
 * UnSupport pull!
 * Created by Sui on 2016/12/12.
 */

public class DummyListViewModule<R> extends BaseListViewModule<R, DummyPullLayout> {
    public DummyListViewModule(Context context, int dataSizeInFullPage) {
        super(context, new DummyPullLayout(), new DummyListStrategy<R>(),dataSizeInFullPage);
    }

    public DummyListViewModule(Context context, IAdapterStrategy<R> fillAdapterStrategy, int dataSizeInFullPage) {
        super(context, new DummyPullLayout(), new DummyListStrategy<>(fillAdapterStrategy),dataSizeInFullPage);
    }
}
