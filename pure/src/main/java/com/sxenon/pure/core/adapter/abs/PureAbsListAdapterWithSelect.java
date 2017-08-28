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

package com.sxenon.pure.core.adapter.abs;

import com.sxenon.pure.core.request.select.BaseSelectSubmitter;
import com.sxenon.pure.core.request.select.strategy.ISelectStrategy;
import com.sxenon.pure.core.viewholder.filler.IListFillerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * PureRecyclerViewAdapterWithSelect
 * Created by Sui on 2017/8/21.
 */

public abstract class PureAbsListAdapterWithSelect<T> extends PureAbsListAdapter<T> {
    private BaseSelectSubmitter selectSubmitter;
    private final ISelectStrategy selectStrategy;

    /**
     * @param container The viewHolder which contain the adapter
     * @param itemViewTypeEntryArray {@link #getItemViewType(int)}
     */
    public PureAbsListAdapterWithSelect(IListFillerViewHolder<T> container, PureAbsListItemViewTypeEntity[] itemViewTypeEntryArray,ISelectStrategy selectStrategy) {
        super(container, itemViewTypeEntryArray);
        this.selectStrategy= selectStrategy;
    }

    @Override
    public void resetAllItems(List<T> values) {
        super.resetAllItems(values);
        selectSubmitter = new BaseSelectSubmitter(selectStrategy,new ArrayList<Boolean>(getItemCount()));
    }

    public void appendOption(T data){
        selectSubmitter.onOptionAppended();
        addItemFromEnd(data);
    }

    public void insertOption(int position,T data){
        selectSubmitter.onOptionInserted(position);
        addItem(position, data);
    }

    public void removeOption(int position){
        selectSubmitter.onOptionRemoved(position);
        removeItem(position);
    }

    public void selectOption(int position){
        selectSubmitter.onOptionSelected(position);
        notifyDataSetChanged();
    }

    public void unSelectOption(int position){
        selectSubmitter.onOptionUnSelected(position);
        notifyDataSetChanged();
    }

    public void selectAllOptions(){
        selectSubmitter.onAllOptionsSelected();
        notifyDataSetChanged();
    }

    public void unSelectAllOptions(){
        selectSubmitter.onAllOptionsUnSelected();
        notifyDataSetChanged();
    }

    public void reverseAllOptions(){
        selectSubmitter.onAllOptionsReversed();
        notifyDataSetChanged();
    }

    public void deletedSelectedOptions(){
        List<Integer> selectedIndexList=selectSubmitter.getDataForSubmit();
        if (selectedIndexList.isEmpty()){
            return;
        }

        selectSubmitter.onSelectedOptionsDeleted();

        List<T> data=getValues();
        for (int i=selectedIndexList.size()-1;i>=0;i--){
            data.remove((int)selectedIndexList.get(i));
        }
        notifyDataSetChanged();
    }

    public BaseSelectSubmitter getSelectSubmitter() {
        return selectSubmitter;
    }
}