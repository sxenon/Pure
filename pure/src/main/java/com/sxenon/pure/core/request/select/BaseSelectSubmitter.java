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

package com.sxenon.pure.core.request.select;

import com.sxenon.pure.core.request.IRequestSubmitter;
import com.sxenon.pure.core.request.select.strategy.ISelectStrategy;
import com.sxenon.pure.core.request.select.strategy.SingleSelectStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Base implement for SelectSubmitter
 * Created by Sui on 2016/11/18.
 */

public  class BaseSelectSubmitter implements IRequestSubmitter<List<Integer>> {
    private List<Boolean> mSelectedFlags;
    private final ISelectStrategy mSelectStrategy;

    /**
     * 一般需结合具体类型的Adapter使用
     * @param selectStrategy MultiSelect,SingleSelect or others
     * @param selectedFlags  selectedFlags in an adapter
     */
    public BaseSelectSubmitter(ISelectStrategy selectStrategy, List<Boolean> selectedFlags) {
        mSelectStrategy = selectStrategy;
        mSelectedFlags = selectedFlags;

        Collections.fill(mSelectedFlags, false);
    }

    public void onOptionAppended() {
        mSelectedFlags.add(false);
    }

    public void onOptionInserted(int position) {
        mSelectedFlags.add(position, false);
    }

    public void onOptionRemoved(int position) {
        mSelectedFlags.remove(position);
    }

    public void onOptionSelected(int position) {
        mSelectStrategy.onOptionSelected(mSelectedFlags,position);
    }

    public void onOptionUnSelected(int position) {
        mSelectStrategy.onOptionUnSelected(mSelectedFlags,position);
    }

    public void onAllOptionsSelected(){
        Collections.fill(mSelectedFlags,true);
    }

    public void onAllOptionsUnSelected(){
        Collections.fill(mSelectedFlags,false);
    }

    public void onAllOptionsReversed(){
        int size=mSelectedFlags.size();
        for (int position=0;position<size;position++){
            mSelectedFlags.set(position,!mSelectedFlags.get(position));
        }
    }

    public void onSelectedOptionsDeleted(){
        Iterator<Boolean> iterator=mSelectedFlags.iterator();
        while (iterator.hasNext()){
            if (iterator.next()){
                iterator.remove();
            }
        }
    }

    @Override
    public List<Integer> getDataForSubmit() {
        final List<Integer> positionList = new ArrayList<>();
        if (mSelectStrategy instanceof SingleSelectStrategy){
            Integer selectedPosition=mSelectedFlags.indexOf(true);
            if (selectedPosition>=0){
                positionList.add(selectedPosition);
            }
            return positionList;
        }
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
                        positionList.add(position);
                    }
                });
        return positionList;
    }

    public List<Boolean> getSelectedFlags() {
        return mSelectedFlags;
    }
}
