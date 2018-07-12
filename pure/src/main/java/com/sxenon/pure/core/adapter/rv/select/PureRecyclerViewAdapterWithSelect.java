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

import com.sxenon.pure.core.adapter.rv.PureRecyclerViewAdapter;
import com.sxenon.pure.core.adapter.rv.PureRecyclerViewItemViewTypeEntity;
import com.sxenon.pure.core.viewmodule.filler.list.IListViewModule;

import java.util.ArrayList;
import java.util.List;

/**
 * PureRecyclerViewAdapterWithSelect
 * Created by Sui on 2017/8/21.
 */

public abstract class PureRecyclerViewAdapterWithSelect<T> extends PureRecyclerViewAdapter<T> {
    private final ISelectInPureRecyclerViewAdapterStrategy<T> selectStrategy;
    private List<Boolean> selectedFlags;

    /**
     * @param container              The viewHolder which contain the adapter
     * @param itemViewTypeEntryArray {@link #getItemViewType(int)}
     */
    public PureRecyclerViewAdapterWithSelect(IListViewModule<T> container, PureRecyclerViewItemViewTypeEntity[] itemViewTypeEntryArray, ISelectInPureRecyclerViewAdapterStrategy<T> selectStrategy) {
        super(container, itemViewTypeEntryArray);
        this.selectStrategy = selectStrategy;
    }

    @Override
    public void resetAllItems(List<T> values) {
        super.resetAllItems(values);
        selectedFlags = new ArrayList<>(getItemCount());
    }

    public void appendOption(T data) {
        selectStrategy.onOptionAppended(selectedFlags, data, this);
    }

    public void removeOption(int position) {
        selectStrategy.onOptionRemoved(selectedFlags, position, this);
    }

    public void selectOption(int position) {
        selectStrategy.onOptionSelected(selectedFlags, position, this);
    }

    public void unSelectOption(int position) {
        selectStrategy.onOptionUnSelected(selectedFlags, position, this);
    }

    public void selectAllOptions() {
        selectStrategy.onAllOptionsSelected(selectedFlags, this);
    }

    public void unSelectAllOptions() {
        selectStrategy.onAllOptionsUnSelected(selectedFlags, this);
    }

    public void reverseAllOptions() {
        selectStrategy.onAllOptionsReversed(selectedFlags, this);
    }

    public void removeSelectedOptions() {
        selectStrategy.onSelectedOptionsRemoved(selectedFlags, this);
    }

    public List<Boolean> getSelectedFlags() {
        return selectedFlags;
    }
}
