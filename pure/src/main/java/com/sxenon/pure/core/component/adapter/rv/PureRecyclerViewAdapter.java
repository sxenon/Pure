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

package com.sxenon.pure.core.component.adapter.rv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxenon.pure.core.component.adapter.IPureAdapter;
import com.sxenon.pure.core.mvp.IViewModule;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adapter for RecyclerView
 * Created by Sui on 2016/12/29.
 */

public abstract class PureRecyclerViewAdapter<T> extends RecyclerView.Adapter<PureRecyclerViewHolder> implements IPureAdapter<T> {
    private final PureRecyclerViewItemViewTypeEntity[] mItemViewTypeEntryArray;
    private final IViewModule mViewHolder;
    private final Object mLock = new Object();
    private List<T> mData = new ArrayList<>();

    /**
     * @param itemViewTypeEntryArray {@link #getItemViewType(int)}
     */
    public PureRecyclerViewAdapter(IViewModule viewHolder, PureRecyclerViewItemViewTypeEntity[] itemViewTypeEntryArray) {
        if (itemViewTypeEntryArray.length == 0) {
            throw new IllegalArgumentException("itemViewTypeEntryArray can`t be empty");
        }
        mItemViewTypeEntryArray = itemViewTypeEntryArray;
        mViewHolder = viewHolder;
    }

    @Override
    public void addItemFromEnd(T value) {
        addItem(mData.size(), value);
    }

    @Override
    public void addItemFromStart(T value) {
        addItem(0, value);
    }

    @Override
    public void addItemsFromStart(List<T> values) {
        addItems(0, values);
    }

    @Override
    public void addItemsFromEnd(List<T> values) {
        addItems(mData.size(), values);
    }

    @Override
    public void addItem(int position, T value) {
        synchronized (mLock) {
            if (position > mData.size() || position < 0 || value == null) {
                return;
            }
            mData.add(position, value);
            notifyItemInserted(position);
        }
    }

    @Override
    public void addItems(int position, List<T> values) {
        synchronized (mLock) {
            if (position > mData.size() || position < 0 || values == null) {
                return;
            }
            mData.addAll(position, values);
            notifyItemRangeInserted(position, values.size());
        }
    }

    @Override
    public void removeItems(List<T> values) {
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
    public void removeItem(T value) {
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
    public List<T> getValues() {
        return mData;
    }

    @Override
    public T getValue(int position) {
        if (position < 0 || position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public void resetAllItems(List<T> values) {
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
    public void setItem(int position, T value) {
        synchronized (mLock) {
            if (value == null || position >= mData.size() || position < 0) {
                return;
            }
            mData.set(position, value);
            notifyItemChanged(position);
        }
    }

    @Override
    public void invalidate(T oldValue, T newValue) {
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
    public PureRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PureRecyclerViewHolder viewHolder = null;

        PureRecyclerViewItemViewTypeEntity itemViewTypeEntity = mItemViewTypeEntryArray[viewType];
        int resourceId = itemViewTypeEntity.getResourceId();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resourceId, null);
        Class<? extends PureRecyclerViewHolder> viewHolderClass = itemViewTypeEntity.getViewHolderClass();
        try {
            Constructor<? extends PureRecyclerViewHolder> constructor = viewHolderClass.getConstructor(IViewModule.class, View.class, PureRecyclerViewAdapter.class);
            viewHolder = constructor.newInstance(mViewHolder, itemView, PureRecyclerViewAdapter.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewHolder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(PureRecyclerViewHolder holder, int position) {
        holder.setIsRecyclable(true);
        holder.fillItemViewByData(holder.itemView, getValue(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * You must override it!
     * Relate with data!
     */
    @Override
    public abstract int getItemViewType(int position);
}
