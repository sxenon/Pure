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

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.adapter.rv.PureRecyclerViewAdapter;
import com.sxenon.pure.core.request.select.strategy.SingleSelectStrategy;
import com.sxenon.pure.core.request.select.strategy.adapter.ISelectInAdapterStrategy;

import java.util.List;

/**
 * Concrete adapter based
 * Created by Sui on 2017/8/28.
 */

public class SingleSelectInRecyclerViewAdapterStrategy extends SingleSelectStrategy implements ISelectInAdapterStrategy {
    @Override
    public void onOptionSelected(List<Boolean> selectedFlags, int position, IPureAdapter adapter) {
        onOptionSelected(selectedFlags, position);
        PureRecyclerViewAdapter recyclerViewAdapter= (PureRecyclerViewAdapter) adapter;
        recyclerViewAdapter.notifyItemRangeChanged(0,recyclerViewAdapter.getItemCount(),true);
    }

    @Override
    public void onOptionUnSelected(List<Boolean> selectedFlags, int position, IPureAdapter adapter) {
        onOptionSelected(selectedFlags, position);
        PureRecyclerViewAdapter recyclerViewAdapter= (PureRecyclerViewAdapter) adapter;
        recyclerViewAdapter.notifyItemRangeChanged(0,recyclerViewAdapter.getItemCount(),false);
    }
}
