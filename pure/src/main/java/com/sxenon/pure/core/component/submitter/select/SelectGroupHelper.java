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

package com.sxenon.pure.core.component.submitter.select;

import android.support.annotation.NonNull;


import com.sxenon.pure.core.component.adapter.IPureAdapter;

import java.util.WeakHashMap;

/**
 * Bind selectGroup with adapter
 * Created by Sui on 2016/11/18.
 */

public class SelectGroupHelper {
    private static final WeakHashMap<IPureAdapter, ISelectGroup> selectGroupInstances = new WeakHashMap<>();

    static <T> void bindAdapterAndSelectGroup(@NonNull IPureAdapter<T> adapter,@NonNull ISelectGroup<T> selectGroup){
        selectGroupInstances.put(adapter,selectGroup);
    }

    /**
     * Find selectGroup by adapter
     */
    public static <T> ISelectGroup<T> findSelectGroupByAdapter(IPureAdapter<T> adapter){
        //noinspection unchecked
        return selectGroupInstances.get(adapter);
    }
}
