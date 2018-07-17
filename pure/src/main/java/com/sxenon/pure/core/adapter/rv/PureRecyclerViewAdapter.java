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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.viewmodule.pull.list.IListViewModule;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adapter for RecyclerView
 * Created by Sui on 2016/12/29.
 */

public abstract class PureRecyclerViewAdapter<R> extends RecyclerView.Adapter<PureRecyclerViewViewHolder> implements IPureAdapter<R> {
    private final PureRecyclerViewItemViewTypeEntity[] mItemViewTypeEntryArray;
    private final IListViewModule<R> mContainer;
    private final Object mLock = new Object();
    private List<R> mData = new ArrayList<>();

    /**
     * @param container              The viewHolder which contain the adapter
     * @param itemViewTypeEntryArray {@link #getItemViewType(int)}
     */
    public PureRecyclerViewAdapter(IListViewModule<R> container, PureRecyclerViewItemViewTypeEntity[] itemViewTypeEntryArray) {
        if (itemViewTypeEntryArray.length == 0) {
            throw new IllegalArgumentException("itemViewTypeEntryArray can`t be empty");
        }

        mItemViewTypeEntryArray = itemViewTypeEntryArray;
        mContainer = container;
        mContainer.setAdapter(this);
    }

    @Override
    public void addItemFromEnd(R value) {
        addItem(mData.size(), value);
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
        addItems(mData.size(), values);
    }

    @Override
    public void addItem(int position, R value) {
        synchronized (mLock) {
            if (position > mData.size() || position < 0 || value == null) {
                return;
            }
            mData.add(position, value);
            notifyItemInserted(position);
        }
    }

    @Override
    public void addItems(int position, List<R> values) {
        synchronized (mLock) {
            if (position > mData.size() || position < 0 || values == null) {
                return;
            }
            mData.addAll(position, values);
            notifyItemRangeInserted(position, values.size());
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
    public void removeItem(int position) {
        synchronized (mLock) {
            if (position >= mData.size() || position < 0) {
                return;
            }
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public void removeItem(R value) {
        synchronized (mLock) {
            int position = mData.indexOf(value);
            if (position >= 0) {
                mData.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    @Override
    public void removeItems(int position, int count) {
        synchronized (mLock) {
            if (position < 0 || count < 1 || position + count > mData.size()) {
                return;
            }
            mData.removeAll(mData.subList(position, position + count));
            notifyItemRangeRemoved(position, count);
        }
    }

    @Override
    public List<R> getValues() {
        return mData;
    }

    @Override
    public R getValue(int position) {
        if (position < 0 || position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public void resetAllItems(List<R> values) {
        synchronized (mLock) {
            if (values == null) {
                mData.clear();
            } else {
                mData = values;
            }
            notifyDataSetChanged();
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
            if (value == null || position >= mData.size() || position < 0) {
                return;
            }
            mData.set(position, value);
            notifyItemChanged(position);
        }
    }

    @Override
    public void invalidate(R oldValue, R newValue) {
        setItem(mData.indexOf(oldValue), newValue);
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {
        synchronized (mLock) {
            if (fromPosition < 0 || fromPosition >= mData.size() || toPosition < 0 || toPosition >= mData.size()) {
                return;
            }
            if (fromPosition == toPosition) {
                return;
            }
            Collections.swap(mData, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    @Override
    public PureRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PureRecyclerViewViewHolder viewHolder = null;

        PureRecyclerViewItemViewTypeEntity itemViewTypeEntity = mItemViewTypeEntryArray[viewType];
        int resourceId = itemViewTypeEntity.getResourceId();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
        Class<? extends PureRecyclerViewViewHolder> viewHolderClass = itemViewTypeEntity.getViewHolderClass();
        try {
            Constructor<? extends PureRecyclerViewViewHolder> constructor = viewHolderClass.getConstructor(View.class, PureRecyclerViewAdapter.class);
            viewHolder = constructor.newInstance(itemView, PureRecyclerViewAdapter.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewHolder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(PureRecyclerViewViewHolder holder, int position) {
        holder.setIsRecyclable(true);
        holder.onSingle(getValue(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public IListViewModule<R> getContainer() {
        return mContainer;
    }

    /**
     * You must override it!
     * Relate with data!
     */
    @Override
    public abstract int getItemViewType(int position);
}
