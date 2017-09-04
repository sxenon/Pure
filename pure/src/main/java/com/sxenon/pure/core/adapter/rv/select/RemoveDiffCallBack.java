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

package com.sxenon.pure.core.adapter.rv.select;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * RemoveDiffCallBack
 * Created by Sui on 2017/8/29.
 */

class RemoveDiffCallBack extends DiffUtil.Callback {
    private final List<Integer> beforeRemoved;
    private final List<Integer> afterRemoved;

    RemoveDiffCallBack(List<Integer> beforeRemoved, List<Integer> afterRemoved) {
        this.beforeRemoved = beforeRemoved;
        this.afterRemoved = afterRemoved;
    }

    @Override
    public int getOldListSize() {
        return beforeRemoved.size();
    }

    @Override
    public int getNewListSize() {
        return afterRemoved.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return (int)beforeRemoved.get(oldItemPosition) == afterRemoved.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }
}
