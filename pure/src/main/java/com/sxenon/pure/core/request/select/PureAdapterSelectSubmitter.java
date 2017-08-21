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

package com.sxenon.pure.core.request.select;

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.request.IRequestSubmitter;

import java.util.List;

/**
 * Wrap PureAdapter and BaseSelectSubmitter
 * Created by Sui on 2017/8/21.
 */

public class PureAdapterSelectSubmitter<T> implements IRequestSubmitter<List<Integer>> {
    private final IPureAdapter<T> mAdapter;
    private final BaseSelectSubmitter mSelectSubmitter;

    /**
     * Use in an adapter
     * See demo to know how to use.
     */
    public PureAdapterSelectSubmitter(IPureAdapter<T> adapter, BaseSelectSubmitter selectSubmitter){
        mAdapter=adapter;
        mSelectSubmitter=selectSubmitter;
    }

    public void appendOption(T data){
        mAdapter.addItemFromEnd(data);
        mSelectSubmitter.onOptionAppended();
    }

    public void insertOption(int position,T data){
        mAdapter.addItem(position, data);
        mSelectSubmitter.onOptionInserted(position);
    }

    public void removeOption(int position){
        mAdapter.removeItem(position);
        mSelectSubmitter.onOptionRemoved(position);
    }

    public void selectOption(int position){
        mSelectSubmitter.onOptionSelected(position);
    }

    public void unSelectOption(int position){
        mSelectSubmitter.onOptionUnSelected(position);
    }

    public void selectAllOptions(){
        mSelectSubmitter.onAllOptionsSelected();
    }

    public void unSelectAllOptions(){
        mSelectSubmitter.onAllOptionsUnSelected();
    }

    public void reverseAllOptions(){
        mSelectSubmitter.onAllOptionsReversed();
    }

    public void deletedSelectedOptions(){
        List<Integer> selectedIndexList=getDataForSubmit();
        if (selectedIndexList.isEmpty()){
            return;
        }

        List<T> data=mAdapter.getValues();
        for (int i=selectedIndexList.size()-1;i>=0;i--){
            data.remove((int)selectedIndexList.get(i));
        }
        mAdapter.notifyDataSetChanged();
        mSelectSubmitter.onSelectedOptionsDeleted();
    }

    @Override
    public List<Integer> getDataForSubmit() {
        return mSelectSubmitter.getDataForSubmit();
    }
}
