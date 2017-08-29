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

package com.sxenon.pure.core.adapter.abs.select;

import com.sxenon.pure.core.adapter.abs.PureAbsListAdapter;
import com.sxenon.pure.core.select.base.ISelectStrategy;
import com.sxenon.pure.core.select.adapter.abs.BaseSelectInAbsListAdapterStrategy;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * BaseSelectInAbsListAdapterStrategy
 * Created by Sui on 2017/8/29.
 */

public class BaseSelectInPureAbsListAdapterStrategy<T> extends BaseSelectInAbsListAdapterStrategy implements ISelectInPureAbsListAdapterStrategy<T> {
    public BaseSelectInPureAbsListAdapterStrategy(ISelectStrategy innerSelectStrategy) {
        super(innerSelectStrategy);
    }

    @Override
    public void onOptionAppended(List<Boolean> selectedFlags, T data, PureAbsListAdapter<T> adapter) {
        selectedFlags.add(false);
        adapter.addItemFromEnd(data);
    }

    @Override
    public void onOptionRemoved(List<Boolean> selectedFlags, int position, PureAbsListAdapter<T> adapter) {
        selectedFlags.remove(position);
        adapter.removeItem(position);
    }

    @Override
    public void onSelectedOptionsRemoved(final List<Boolean> selectedFlags, final PureAbsListAdapter<T> adapter) {
        final int size = selectedFlags.size();
        Observable.range(0, size)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return size - integer;
                    }
                })
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer position) {
                        return selectedFlags.get(position);
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer position) {
                        selectedFlags.remove((int) position);
                        adapter.removeItem(position);
                    }
                });
        adapter.notifyDataSetChanged();
    }
}
