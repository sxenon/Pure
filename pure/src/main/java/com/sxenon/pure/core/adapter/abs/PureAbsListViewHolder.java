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

import android.content.Context;
import android.view.View;

import com.sxenon.pure.core.adapter.IAdapterViewHolder;
import com.sxenon.pure.core.viewholder.IViewHolder;

/**
 * ViewHolder for AbsList
 * Created by Sui on 2016/12/25.
 */

public abstract class PureAbsListViewHolder<T> implements IAdapterViewHolder<T> {
    private final int position;
    private final PureAbsListAdapter adapter;
    private final IViewHolder adapterContainer;
    public final View itemView;

    public PureAbsListViewHolder(View itemView, PureAbsListAdapter adapter, int position) {
        this.position = position;
        this.adapter = adapter;
        this.adapterContainer = adapter.getContainer();
        this.itemView = itemView;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PureAbsListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public Context getContext() {
        return adapterContainer.getContext();
    }
}
