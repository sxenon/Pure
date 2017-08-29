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

package com.sxenon.pure.core.request.select.adapter.rv;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import com.sxenon.pure.core.request.select.base.ISelectStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete adapter based
 * Created by Sui on 2017/8/28.
 */

public abstract class BaseSelectInRecyclerViewAdapterStrategy implements ISelectInRecyclerViewAdapterStrategy {
    private ISelectStrategy innerSelectStrategy;

    public BaseSelectInRecyclerViewAdapterStrategy(ISelectStrategy innerSelectStrategy) {
        this.innerSelectStrategy = innerSelectStrategy;
    }

    @Override
    public void onOptionSelected(List<Boolean> selectedFlags, int position, RecyclerView.Adapter adapter) {
        if (selectedFlags.get(position)) {
            return;
        }
        List<Boolean> oldSelectedFlags = new ArrayList<>(selectedFlags);
        innerSelectStrategy.onOptionSelected(selectedFlags, position);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(genSelectDiffCallBack(oldSelectedFlags, selectedFlags), false);
        result.dispatchUpdatesTo(adapter);
    }

    @Override
    public void onOptionUnSelected(List<Boolean> selectedFlags, int position, RecyclerView.Adapter adapter) {
        if (!selectedFlags.get(position)) {
            return;
        }
        List<Boolean> oldSelectedFlags = new ArrayList<>(selectedFlags);
        innerSelectStrategy.onOptionUnSelected(selectedFlags, position);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(genSelectDiffCallBack(oldSelectedFlags, selectedFlags), false);
        result.dispatchUpdatesTo(adapter);
    }

    @Override
    public void onAllOptionsReversed(List<Boolean> selectedFlags, RecyclerView.Adapter adapter) {
        List<Boolean> oldSelectedFlags = new ArrayList<>(selectedFlags);
        innerSelectStrategy.onAllOptionsReversed(selectedFlags);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(genSelectDiffCallBack(oldSelectedFlags, selectedFlags), false);
        result.dispatchUpdatesTo(adapter);
    }

    @Override
    public void onAllOptionsSelected(List<Boolean> selectedFlags, RecyclerView.Adapter adapter) {
        List<Boolean> oldSelectedFlags = new ArrayList<>(selectedFlags);
        innerSelectStrategy.onAllOptionsSelected(selectedFlags);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(genSelectDiffCallBack(oldSelectedFlags, selectedFlags), false);
        result.dispatchUpdatesTo(adapter);
    }

    @Override
    public void onAllOptionsUnSelected(List<Boolean> selectedFlags, RecyclerView.Adapter adapter) {
        List<Boolean> oldSelectedFlags = new ArrayList<>(selectedFlags);
        innerSelectStrategy.onAllOptionsUnSelected(selectedFlags);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(genSelectDiffCallBack(oldSelectedFlags, selectedFlags), false);
        result.dispatchUpdatesTo(adapter);
    }

    protected abstract SelectDiffCallBack genSelectDiffCallBack(List<Boolean> oldSelectedFlags, List<Boolean> newSelectedFlags);

}
