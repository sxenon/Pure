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

package com.sxenon.pure.core.adapter.abs;

import android.view.View;

import com.sxenon.pure.core.adapter.IAdapterViewHolder;
import com.sxenon.pure.core.mvp.IView;

/**
 * ViewHolder for AbsList
 * Created by Sui on 2016/12/25.
 */

public abstract class PureAbsViewHolder<T> implements IAdapterViewHolder<T> {
    private final int position;
    private final PureAbsListAdapter adapter;
    private final IView adapterContainer;
    public final View itemView;

    public PureAbsViewHolder(View itemView, PureAbsListAdapter adapter, int position, IView adapterContainer) {
        this.position = position;
        this.adapter = adapter;
        this.adapterContainer = adapterContainer;
        this.itemView = itemView;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @SuppressWarnings("unchecked")
    public PureAbsListAdapter getAdapter() {
        return adapter;
    }

    public IView getAdapterContainer() {
        return adapterContainer;
    }
}
