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

package com.sxenon.pure.core.request.select.adapter.rv;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * SelectDiffCallBack
 * Created by Sui on 2017/8/29.
 */

public class SelectDiffCallBack extends DiffUtil.Callback {
    private List<Boolean> oldSelectedFlags;
    private List<Boolean> newSelectedFlags;

    public SelectDiffCallBack(List<Boolean> oldSelectedFlags, List<Boolean> newSelectedFlags) {
        this.oldSelectedFlags = oldSelectedFlags;
        this.newSelectedFlags = newSelectedFlags;
    }

    @Override
    public int getOldListSize() {
        return oldSelectedFlags.size();
    }

    @Override
    public int getNewListSize() {
        return newSelectedFlags.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemPosition==newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldSelectedFlags.get(oldItemPosition)==newSelectedFlags.get(newItemPosition);
    }

    public static void dispatchUpdatesToAdapter(List<Boolean> oldSelectedFlags, List<Boolean> newSelectedFlags, RecyclerView.Adapter adapter){
        DiffUtil.DiffResult result=DiffUtil.calculateDiff(new SelectDiffCallBack(oldSelectedFlags,newSelectedFlags),false);
        result.dispatchUpdatesTo(adapter);
    }


}
