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

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.viewmodule.pull.list.IListViewModule;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adapter for AbsList
 * Created by Sui on 2016/12/25.
 */

public abstract class PureAbsListAdapter<R> extends BaseAdapter implements IPureAdapter<R> {

    private final Object mLock = new Object();
    private final PureAbsListItemViewTypeEntity[] mItemViewTypeEntryArray;
    private final IListViewModule<R> mContainer;
    private final List<R> mData = new ArrayList<>();

    /**
     * @param container              The viewHolder which contain the adapter
     * @param itemViewTypeEntryArray {@link #getItemViewType(int)}
     */
    public PureAbsListAdapter(IListViewModule<R> container, @NonNull PureAbsListItemViewTypeEntity[] itemViewTypeEntryArray) {
        if (itemViewTypeEntryArray.length == 0) {
            throw new IllegalArgumentException("itemViewTypeEntryArray can`t be empty");
        }

        mItemViewTypeEntryArray = itemViewTypeEntryArray;
        mContainer = container;
        mContainer.setAdapter(this);
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void addItemFromEnd(R value) {
        addItem(getItemCount(), value);
    }

    @Override
    public void addItemFromStart(R value) {
        addItem(0, value);
    }

    @Override
    public void addItemsFromStart(List<R> values) {
        addItems(0, values);
    }

    @Override
    public void addItemsFromEnd(List<R> values) {
        addItems(getItemCount(), values);
    }

    @Override
    public void addItem(int position, R value) {
        synchronized (mLock) {
            if (position < 0 || position > getCount() || value == null) {
                return;
            }
            mData.add(position, value);
            notifyDataSetChanged();
        }
    }

    @Override
    public void addItems(int position, List<R> values) {
        synchronized (mLock) {
            if (position < 0 || position > getCount() || values == null) {
                return;
            }
            mData.addAll(position, values);
            notifyDataSetChanged();
        }
    }

    @Override
    public void removeItems(List<R> values) {
        synchronized (mLock) {
            mData.removeAll(values);
            notifyDataSetChanged();
        }
    }

    @Override
    public void removeItems(int position, int count) {
        synchronized (mLock) {
            if (position < 0 || count < 1 || position + count > getCount()) {
                return;
            }
            removeItems(mData.subList(position, position + count));
        }
    }

    @Override
    public void removeItem(int position) {
        synchronized (mLock) {
            if (position < 0 || position >= mData.size()) {
                return;
            }
            mData.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public void removeItem(R value) {
        synchronized (mLock) {
            int position = mData.indexOf(value);
            removeItem(position);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<R> getValues() {
        return mData;
    }

    @Override
    public R getValue(int position) {
        if (position < 0 || position >= getCount()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public void resetAllItems(List<R> values) {
        if (values == null) {
            clearAllItems();
        } else {
            synchronized (mLock) {
                mData.clear();
                mData.addAll(values);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void clearAllItems() {
        synchronized (mLock) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void setItem(int position, R value) {
        synchronized (mLock) {
            if (value == null || position < 0 || position >= getCount()) {
                return;
            }
            mData.set(position, value);
            notifyDataSetChanged();
        }
    }

    @Override
    public void invalidate(R oldValue, R newValue) {
        setItem(mData.indexOf(oldValue), newValue);
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {
        synchronized (mLock) {
            if (fromPosition < 0 || fromPosition >= getCount() || toPosition < 0 || toPosition >= getCount()) {
                return;
            }
            if (fromPosition == toPosition) {
                return;
            }
            Collections.swap(mData, fromPosition, toPosition);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        PureAbsListViewHolder viewHolder = null;
        if (convertView == null) {
            PureAbsListItemViewTypeEntity itemViewTypeEntity = mItemViewTypeEntryArray[getItemViewType(position)];
            convertView = LayoutInflater.from(mContainer.getContext()).inflate(itemViewTypeEntity.getResourceId(), null);
            Class<? extends PureAbsListViewHolder> viewHolderClass = itemViewTypeEntity.getViewHolderClass();
            try {
                Constructor<? extends PureAbsListViewHolder> constructor = viewHolderClass.getConstructor(View.class, PureAbsListAdapter.class, Integer.class);
                viewHolder = constructor.newInstance(convertView, PureAbsListAdapter.this, position);
            } catch (Exception e) {
                e.printStackTrace();
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PureAbsListViewHolder) convertView.getTag();
        }
        //noinspection ConstantConditions,unchecked
        viewHolder.onSingleDataFetched(getItem(position));
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return mItemViewTypeEntryArray.length;
    }

    public IListViewModule<R> getContainer() {
        return mContainer;
    }

    /**
     * You must override it!
     */
    @Override
    public abstract int getItemViewType(int position);
}
