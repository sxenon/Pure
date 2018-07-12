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

package com.sxenon.pure.core.viewmodule.filler.single.dummy;

import android.content.Context;

import com.sxenon.pure.core.viewmodule.filler.DummyPullLayout;
import com.sxenon.pure.core.viewmodule.filler.single.BaseSingleViewModule;

/**
 * SingleData implement for DummyFillerViewHolder
 * Created by Sui on 2017/8/19.
 */

public class DummySingleViewModule<R> extends BaseSingleViewModule<R, DummyPullLayout> {
    /**
     * Constructor
     *
     * @param context    上下文
     * @param pullLayout 刷新容器
     */
    public DummySingleViewModule(Context context, DummyPullLayout pullLayout) {
        super(context, pullLayout, new DummySingleStrategy<R>());
    }
}