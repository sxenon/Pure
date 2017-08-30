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

package com.sxenon.pure.core.adapter.rv.select;

import android.support.v7.util.DiffUtil;

import com.sxenon.pure.core.adapter.rv.PureRecyclerViewAdapter;
import com.sxenon.pure.core.select.adapter.rv.BaseSelectInRecyclerViewAdapterStrategy;
import com.sxenon.pure.core.select.base.ISelectStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseSelectInPureRecyclerViewAdapterStrategy
 * Created by Sui on 2017/8/29.
 */

public abstract class BaseSelectInPureRecyclerViewAdapterStrategy<T> extends BaseSelectInRecyclerViewAdapterStrategy implements ISelectInPureRecyclerViewAdapterStrategy<T> {
    public BaseSelectInPureRecyclerViewAdapterStrategy(ISelectStrategy innerSelectStrategy) {
        super(innerSelectStrategy);
    }

    @Override
    public void onOptionAppended(List<Boolean> selectedFlags, T data, PureRecyclerViewAdapter<T> adapter) {
        selectedFlags.add(false);
        adapter.addItemFromEnd(data);
    }

    @Override
    public void onOptionRemoved(List<Boolean> selectedFlags, int position, PureRecyclerViewAdapter<T> adapter) {
        selectedFlags.remove(position);
        adapter.removeItem(position);
    }

    @Override
    public void onSelectedOptionsRemoved(List<Boolean> selectedFlags, PureRecyclerViewAdapter<T> adapter) {
        int size = selectedFlags.size();
        List<Integer> beforeRemoved = new ArrayList<>();
        for (int position = 0; position < size; position++) {
            beforeRemoved.add(position);
        }

        boolean selected = false;
        List<Integer> afterRemoved = new ArrayList<>(beforeRemoved);
        for (int position = size - 1; position >= 0; position--) {
            if (selectedFlags.get(position)) {
                afterRemoved.remove(position);
                adapter.removeItem(position);
                selected=true;
            }
        }

        if (selected){
            selectedFlags.remove(true);

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new RemoveDiffCallBack(beforeRemoved, afterRemoved), false);
            result.dispatchUpdatesTo(adapter);
        }
    }

}
