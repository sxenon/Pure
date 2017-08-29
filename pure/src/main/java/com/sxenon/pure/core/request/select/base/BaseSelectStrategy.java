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

package com.sxenon.pure.core.request.select.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * BaseSelectStrategy
 * Created by Sui on 2017/8/29.
 */

public abstract class BaseSelectStrategy implements ISelectStrategy {
    @Override
    public void onAllOptionsReversed(List<Boolean> selectedFlags) {
        int size=selectedFlags.size();
        for (int position=0;position<size;position++){
            selectedFlags.set(position,!selectedFlags.get(position));
        }
    }

    @Override
    public void onAllOptionsSelected(List<Boolean> selectedFlags) {
        Collections.fill(selectedFlags,true);
    }

    @Override
    public void onAllOptionsUnSelected(List<Boolean> selectedFlags) {
        Collections.fill(selectedFlags,false);
    }

    @Override
    public void onOptionAppended(List<Boolean> selectedFlags) {
        selectedFlags.add(false);
    }

    @Override
    public void onOptionInserted(List<Boolean> selectedFlags, int position) {
        selectedFlags.add(position,false);
    }

    @Override
    public void onOptionRemoved(List<Boolean> selectedFlags, int position) {
        selectedFlags.remove(position);
    }

    @Override
    public List<Integer> onSelectedOptionsRemoved(final List<Boolean> selectedFlags) {
        final List<Integer> selectedPositionList=new ArrayList<>();
        if (this instanceof SingleSelectStrategy){
            Integer selectedPosition=selectedFlags.indexOf(true);
            if (selectedPosition>=0){
                selectedPositionList.add(selectedPosition);
            }
            selectedFlags.remove((int)selectedPosition);
            return selectedPositionList;
        }
        final int size=selectedFlags.size();
        Observable.range(0, size)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return size-integer;
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
                        selectedFlags.remove((int)position);
                        selectedPositionList.add(position);
                    }
                });
        return selectedPositionList;
    }
}
