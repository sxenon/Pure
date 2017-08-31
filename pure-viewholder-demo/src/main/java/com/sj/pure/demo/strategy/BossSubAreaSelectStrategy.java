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

package com.sj.pure.demo.strategy;

import android.support.v7.widget.RecyclerView;

import com.sxenon.pure.core.select.adapter.rv.BaseSelectInRecyclerViewAdapterStrategy;
import com.sxenon.pure.core.select.adapter.rv.SelectDiffCallBack;

import java.util.List;


/**
 * Boss直聘上 区域选择的效果实现
 * Created by Sui on 2017/8/29.
 */


public class BossSubAreaSelectStrategy extends BaseSelectInRecyclerViewAdapterStrategy<BossSubAreaInnerSelectStrategy> {

    public BossSubAreaSelectStrategy(BossSubAreaInnerSelectStrategy innerSelectStrategy) {
        super(innerSelectStrategy);
    }

    @Override
    public void onAllOptionsReversed(List<Boolean> selectedFlags, RecyclerView.Adapter adapter) {
        throw new UnsupportedOperationException("UnSupport for reverse all");
    }

    @Override
    public void onAllOptionsSelected(List<Boolean> selectedFlags, RecyclerView.Adapter adapter) {
        throw new UnsupportedOperationException("UnSupport for select all");
    }

    @Override
    public void onAllOptionsUnSelected(List<Boolean> selectedFlags, RecyclerView.Adapter adapter) {
       throw new UnsupportedOperationException("UnSupport for unSelect all");
    }

    @Override
    public void onOptionSelected(List<Boolean> selectedFlags, int position, RecyclerView.Adapter adapter) {
        super.onOptionSelected(selectedFlags, position, adapter);
    }

    @Override
    public void onOptionUnSelected(List<Boolean> selectedFlags, int position, RecyclerView.Adapter adapter) {
        if (position == 0) {
            return;
        }
        super.onOptionUnSelected(selectedFlags, position, adapter);
    }

    @Override
    protected SelectDiffCallBack genSelectDiffCallBack(List<Boolean> oldSelectedFlags, List<Boolean> newSelectedFlags) {
        return new SelectDiffCallBack(oldSelectedFlags, newSelectedFlags);
    }

}

