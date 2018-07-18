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

package com.sxenon.pure.core.viewmodule.pull.single.dummy;

import android.content.Context;

import com.sxenon.pure.core.viewmodule.pull.DummyPullLayout;
import com.sxenon.pure.core.viewmodule.pull.single.BaseSingleViewModule;

/**
 * SingleData implement for DummyFillerViewHolder
 * Created by Sui on 2017/8/19.
 */

public abstract class DummySingleViewModule<R> extends BaseSingleViewModule<R, DummyPullLayout> {
    /**
     * Constructor
     *
     * @param context    上下文
     */
    public DummySingleViewModule(Context context) {
        super(context, new DummyPullLayout(), new DummySingleStrategy<R>());
    }
}
