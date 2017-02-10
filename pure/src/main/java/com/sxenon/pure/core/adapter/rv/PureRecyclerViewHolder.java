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

package com.sxenon.pure.core.adapter.rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sxenon.pure.core.adapter.IPureViewHolder;
import com.sxenon.pure.core.mvp.IView;

/**
 * ViewHolder for RecyclerView
 * Created by Sui on 2016/12/29.
 */

public abstract class PureRecyclerViewHolder<T> extends RecyclerView.ViewHolder implements IPureViewHolder<T> {
    private final PureRecyclerViewAdapter adapter;
    private final IView adapterContainer;

    public PureRecyclerViewHolder(View itemView, PureRecyclerViewAdapter adapter,IView adapterContainer) {
        super(itemView);
        this.adapter = adapter;
        this.adapterContainer = adapterContainer;
    }

    @SuppressWarnings("unchecked")
    public PureRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    public IView getAdapterContainer() {
        return adapterContainer;
    }
}
