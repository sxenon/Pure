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

package com.sxenon.pure.core.viewholder.submitter.select.multi;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.viewholder.submitter.select.BaseSelectViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * MultiSelectViewHolder
 * Created by Sui on 2017/8/20.
 */

public class MultiSelectViewHolder<T> extends BaseSelectViewHolder<T> {

    public MultiSelectViewHolder(@NonNull Context context, @NonNull IPureAdapter<T> adapter) {
        super(context, adapter, new MultiSelectStrategy());
    }

    public void onAllOptionsSelected(){
        Collections.fill(getSelectedFlags(),true);
    }

    public void onAllOptionsUnSelected(){
        Collections.fill(getSelectedFlags(),false);
    }

    public void onAllOptionsReversed(){
        List<Boolean> selectedFlags=getSelectedFlags();
        int size=selectedFlags.size();
        for (int position=0;position<size;position++){
            selectedFlags.set(position,!selectedFlags.get(position));
        }
    }

    public void onSelectedOptionsDeleted(List<Integer> selectedIndexList){
        int newSize=getSelectedFlags().size()-selectedIndexList.size();
        onOptionsReset(newSize);
    }
}
