package com.sxenon.pure.component.submitter.select;

import android.support.annotation.NonNull;


import com.sxenon.pure.adapter.IPureAdapter;

import java.util.WeakHashMap;

/**
 * Find selectGroup by adapter
 * Created by Sui on 2016/11/18.
 */

public class SelectGroupHelper {
    private static final WeakHashMap<IPureAdapter, ISelectGroup> selectGroupInstances = new WeakHashMap<>();

    static <T> void bindAdapterAndSelectGroup(@NonNull IPureAdapter<T> adapter,@NonNull ISelectGroup<T> selectGroup){
        selectGroupInstances.put(adapter,selectGroup);
    }

    public static <T> ISelectGroup<T> findSelectGroupByAdapter(IPureAdapter<T> adapter){
        //noinspection unchecked
        return selectGroupInstances.get(adapter);
    }
}
