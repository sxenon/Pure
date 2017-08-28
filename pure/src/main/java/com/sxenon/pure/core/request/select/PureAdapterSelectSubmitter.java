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
import com.sxenon.pure.core.request.select.strategy.adapter.ISelectInAdapterStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrap PureAdapter and BaseSelectSubmitter
 * Created by Sui on 2017/8/21.
 */

public class PureAdapterSelectSubmitter<T> implements IRequestSubmitter<List<Integer>> {
    private final IPureAdapter<T> mAdapter;
    private final BaseSelectSubmitter mSelectSubmitter;
    private final ISelectInAdapterStrategy mSelectInAdapterStrategy;
    /**
     * Use in an adapter
     */
    public PureAdapterSelectSubmitter(IPureAdapter<T> adapter, ISelectInAdapterStrategy selectInAdapterStrategy){
        mAdapter=adapter;
        mSelectInAdapterStrategy=selectInAdapterStrategy;
        mSelectSubmitter=new BaseSelectSubmitter(selectInAdapterStrategy,new ArrayList<Boolean>(adapter.getItemCount()));
    }

    public void appendOption(T data){
        mSelectSubmitter.onOptionAppended();
        mAdapter.addItemFromEnd(data);
    }

    public void insertOption(int position,T data){
        mSelectSubmitter.onOptionInserted(position);
        mAdapter.addItem(position, data);
    }

    public void removeOption(int position){
        mSelectSubmitter.onOptionRemoved(position);
        mAdapter.removeItem(position);
    }

    public void selectOption(int position){
        mSelectInAdapterStrategy.onOptionSelected(mSelectSubmitter.getSelectedFlags(),position,mAdapter);
    }

    public void unSelectOption(int position){
        mSelectInAdapterStrategy.onOptionUnSelected(mSelectSubmitter.getSelectedFlags(),position,mAdapter);
    }

    public void selectAllOptions(){
        mSelectSubmitter.onAllOptionsSelected();
        mAdapter.notifyDataSetChanged();
    }

    public void unSelectAllOptions(){
        mSelectSubmitter.onAllOptionsUnSelected();
        mAdapter.notifyDataSetChanged();
    }

    public void reverseAllOptions(){
        mSelectSubmitter.onAllOptionsReversed();
        mAdapter.notifyDataSetChanged();
    }

    public void deletedSelectedOptions(){
        List<Integer> selectedIndexList=getDataForSubmit();
        if (selectedIndexList.isEmpty()){
            return;
        }

        mSelectSubmitter.onSelectedOptionsDeleted();

        List<T> data=mAdapter.getValues();
        for (int i=selectedIndexList.size()-1;i>=0;i--){
            data.remove((int)selectedIndexList.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public List<Integer> getDataForSubmit() {
        return mSelectSubmitter.getDataForSubmit();
    }
}
