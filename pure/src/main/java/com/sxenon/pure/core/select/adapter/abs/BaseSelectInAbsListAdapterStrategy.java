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

package com.sxenon.pure.core.select.adapter.abs;

import android.widget.BaseAdapter;

import com.sxenon.pure.core.select.base.ISelectStrategy;

import java.util.List;

/**
 * BaseSelectInAbsListAdapterStrategy
 * Created by Sui on 2017/8/29.
 */

public class BaseSelectInAbsListAdapterStrategy<S extends ISelectStrategy> implements ISelectInAbsListAdapterStrategy {
    private final S innerSelectStrategy;

    public BaseSelectInAbsListAdapterStrategy(S innerSelectStrategy) {
        this.innerSelectStrategy = innerSelectStrategy;
    }

    @Override
    public void onOptionSelected(List<Boolean> selectedFlags, int position, BaseAdapter adapter) {
        if (selectedFlags.get(position)) {
            return;
        }

        innerSelectStrategy.onOptionSelected(selectedFlags, position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onOptionUnSelected(List<Boolean> selectedFlags, int position, BaseAdapter adapter) {
        if (!selectedFlags.get(position)) {
            return;
        }

        innerSelectStrategy.onOptionUnSelected(selectedFlags, position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAllOptionsReversed(List<Boolean> selectedFlags, BaseAdapter adapter) {
        innerSelectStrategy.onAllOptionsReversed(selectedFlags);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAllOptionsSelected(List<Boolean> selectedFlags, BaseAdapter adapter) {
        innerSelectStrategy.onAllOptionsSelected(selectedFlags);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAllOptionsUnSelected(List<Boolean> selectedFlags, BaseAdapter adapter) {
        innerSelectStrategy.onAllOptionsUnSelected(selectedFlags);
        adapter.notifyDataSetChanged();
    }

    public S getInnerSelectStrategy() {
        return innerSelectStrategy;
    }
}
