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

package com.sxenon.pure.core.viewholder.submitter.select;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.adapter.IPureAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Base impl for ISelectViewHolder
 * Created by Sui on 2016/11/18.
 */

public abstract class BaseSelectViewHolder<T> implements ISelectViewHolder<T> {
    private final Context mContext;
    private final IPureAdapter<T> mAdapter;
    private List<Boolean> mSelectedFlags;
    private final ISelectViewHolder.SelectStrategy mSelectStrategy;

    /**
     * TODO
     * Override {@link IPureAdapter#resetAllItems(List)},and call this int it;
     * @param context ?
     * @param adapter adapter
     * @param selectStrategy SelectStrategy
     */
    public BaseSelectViewHolder(@NonNull Context context, @NonNull IPureAdapter<T> adapter, ISelectViewHolder.SelectStrategy selectStrategy) {
        mContext = context;
        mAdapter = adapter;
        mSelectStrategy = selectStrategy;
        mSelectedFlags = new ArrayList<>(mAdapter.getItemCount());
        Collections.fill(mSelectedFlags, false);
    }

    @Override
    public void onOptionAppended() {
        mSelectedFlags.add(false);
    }

    @Override
    public void onOptionInserted(int position) {
        mSelectedFlags.add(position, false);
    }

    @Override
    public void onOptionRemoved(int position) {
        mSelectedFlags.remove(position);
    }

    @Override
    public void onOptionsReset(int size) {
        mSelectedFlags = new ArrayList<>(size);
        Collections.fill(mSelectedFlags, false);
    }

    @Override
    public void onOptionsReset() {
        Collections.fill(mSelectedFlags, false);
    }

    @Override
    public void onOptionSelected(int position) {
        mSelectStrategy.onOptionSelected(mSelectedFlags, position);
    }

    @Override
    public void onOptionUnSelected(int position) {
        mSelectStrategy.onOptionUnSelected(mSelectedFlags, position);
    }

    @Override
    public List<T> getDataForSubmit() {
        final List<T> selectedOptionList = new ArrayList<>();
        Observable.range(0, mSelectedFlags.size())
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer position) {
                        return mSelectedFlags.get(position);
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer position) {
                        selectedOptionList.add(mAdapter.getValues().get(position));
                    }
                });
        return selectedOptionList;
    }

    public List<Integer> getSelectedIndexList() {
        final List<Integer> indexList = new ArrayList<>();
        Observable.range(0, mSelectedFlags.size())
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer position) {
                        return mSelectedFlags.get(position);
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer position) {
                        indexList.add(position);
                    }
                });
        return indexList;
    }

    @NonNull
    @Override
    public Context getContext() {
        return mContext;
    }

    public IPureAdapter<T> getAdapter() {
        return mAdapter;
    }

    public List<Boolean> getSelectedFlags() {
        return mSelectedFlags;
    }
}
