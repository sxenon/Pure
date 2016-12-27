package com.sxenon.pure.core.component.adapter.abs;

import com.sxenon.pure.core.component.adapter.IPureViewHolder;

/**
 * Created by Sui on 2016/12/25.
 */

public abstract class PureAbsViewHolder<T> implements IPureViewHolder<T> {
    private final int position;
    private final PureAbsListAdapter<T> adapter;

    public PureAbsViewHolder(PureAbsListAdapter<T> adapter,int position){
        this.position=position;
        this.adapter=adapter;
    }

    @Override
    public int getPosition() {
        return position;
    }

    public PureAbsListAdapter<T> getAdapter(){
        return adapter;
    }
}
