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

package com.sj.pure.demo.adapter;

import com.sj.pure.demo.bean.ResponseBean;
import com.sxenon.pure.core.adapter.rv.PureRecyclerViewAdapter;
import com.sxenon.pure.core.adapter.rv.PureRecyclerViewItemViewTypeEntity;
import com.sxenon.pure.core.request.select.PureAdapterSelectSubmitter;
import com.sxenon.pure.core.request.select.multi.MultiSelectSubmitter;
import com.sxenon.pure.core.viewholder.filler.IListFillerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sui on 2017/8/21.
 */

public class DemoPureRecyclerViewAdapterWithSelect extends PureRecyclerViewAdapter<ResponseBean> {
    private PureAdapterSelectSubmitter<ResponseBean> selectSubmitter;

    /**
     * @param container The viewHolder which contain the adapter
     * @param itemViewTypeEntryArray {@link #getItemViewType(int)}
     */
    public DemoPureRecyclerViewAdapterWithSelect(IListFillerViewHolder<ResponseBean> container, PureRecyclerViewItemViewTypeEntity[] itemViewTypeEntryArray) {
        super(container, itemViewTypeEntryArray);
    }

    @Override
    public void resetAllItems(List<ResponseBean> values) {
        super.resetAllItems(values);
        selectSubmitter = new PureAdapterSelectSubmitter<>(this,new MultiSelectSubmitter(new ArrayList<Boolean>(getItemCount())));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
