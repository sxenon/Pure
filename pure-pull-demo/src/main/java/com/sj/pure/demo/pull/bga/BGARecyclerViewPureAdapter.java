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

package com.sj.pure.demo.pull.bga;

import com.sxenon.pure.core.adapter.IPureAdapter;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;

/**
 * BGARecyclerViewPureAdapter
 * Created by Sui on 2017/2/5.
 */

public class BGARecyclerViewPureAdapter<T> implements IPureAdapter<T> {
    private final BGARecyclerViewAdapter<T> bgaRecyclerViewAdapter;

    BGARecyclerViewPureAdapter(BGARecyclerViewAdapter<T> bgaRecyclerViewAdapter) {
        this.bgaRecyclerViewAdapter = bgaRecyclerViewAdapter;
    }

    @Override
    public int getItemCount() {
        return bgaRecyclerViewAdapter.getItemCount();
    }

    @Override
    public void addItemFromEnd(T value) {
        bgaRecyclerViewAdapter.addLastItem(value);
    }

    @Override
    public void addItemFromStart(T value) {
        bgaRecyclerViewAdapter.addFirstItem(value);
    }

    @Override
    public void addItemsFromStart(List<T> values) {
        bgaRecyclerViewAdapter.addNewData(values);
    }

    @Override
    public void addItemsFromEnd(List<T> values) {
        bgaRecyclerViewAdapter.addMoreData(values);
    }

    @Override
    public void addItem(int position, T value) {
        bgaRecyclerViewAdapter.addItem(position, value);
    }

    @Override
    public void addItems(int position, List<T> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeItems(List<T> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeItem(int position) {
        bgaRecyclerViewAdapter.removeItem(position);
    }

    @Override
    public void removeItem(T value) {
        bgaRecyclerViewAdapter.removeItem(value);
    }

    @Override
    public void removeItems(int position, int count) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> getValues() {
        return bgaRecyclerViewAdapter.getData();
    }

    @Override
    public T getValue(int position) {
        return bgaRecyclerViewAdapter.getItem(position);
    }

    @Override
    public void resetAllItems(List<T> values) {
        bgaRecyclerViewAdapter.setData(values);
    }

    @Override
    public void clearAllItems() {
        bgaRecyclerViewAdapter.clear();
    }

    @Override
    public void setItem(int position, T value) {
        bgaRecyclerViewAdapter.setItem(position, value);
    }

    @Override
    public void invalidate(T oldValue, T newValue) {
        bgaRecyclerViewAdapter.setItem(oldValue, newValue);
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {
        bgaRecyclerViewAdapter.moveItem(fromPosition, toPosition);
    }
}
