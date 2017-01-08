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

package com.sxenon.pure.core.component.adapter;

import android.view.View;

import com.sxenon.pure.core.mvp.IViewModule;

/**
 * Interface for adapter`s viewHolder
 * Created by Sui on 2016/12/25.
 */

public interface IPureViewHolder<T> {
    int getPosition();

    void fillItemViewByData(View itemView,T data);

    <A extends IPureAdapter> A getAdapter();

    IViewModule getViewModule();
}
