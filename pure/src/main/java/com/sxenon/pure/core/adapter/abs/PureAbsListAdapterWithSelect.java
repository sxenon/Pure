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

import com.sxenon.pure.core.request.select.PureAdapterSelectSubmitter;
import com.sxenon.pure.core.request.select.multi.MultiSelectSubmitter;
import com.sxenon.pure.core.viewholder.filler.IListFillerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * PureRecyclerViewAdapterWithSelect
 * Created by Sui on 2017/8/21.
 */

public abstract class PureAbsListAdapterWithSelect<T> extends PureAbsListAdapter<T> {
    private PureAdapterSelectSubmitter<T> selectSubmitter;

    /**
     * @param container The viewHolder which contain the adapter
     * @param itemViewTypeEntryArray {@link #getItemViewType(int)}
     */
    public PureAbsListAdapterWithSelect(IListFillerViewHolder<T> container, PureAbsListItemViewTypeEntity[] itemViewTypeEntryArray) {
        super(container, itemViewTypeEntryArray);
    }

    @Override
    public void resetAllItems(List<T> values) {
        super.resetAllItems(values);
        selectSubmitter = new PureAdapterSelectSubmitter<>(this,new MultiSelectSubmitter(new ArrayList<Boolean>(getItemCount())));
    }

    public void appendOption(T data){
        selectSubmitter.appendOption(data);
    }

    public void insertOption(int position,T data){
        selectSubmitter.insertOption(position, data);
    }

    public void removeOption(int position){
        selectSubmitter.removeOption(position);
    }

    public void selectOption(int position){
        selectSubmitter.selectOption(position);
    }

    public void unSelectOption(int position){
        selectSubmitter.unSelectOption(position);
    }

    public void selectAllOptions(){
        selectSubmitter.selectAllOptions();
    }

    public void unSelectAllOptions(){
        selectSubmitter.unSelectAllOptions();
    }

    public void reverseAllOptions(){
        selectSubmitter.reverseAllOptions();
    }

    public void deletedSelectedOptions(){
        selectSubmitter.deletedSelectedOptions();
    }

}
