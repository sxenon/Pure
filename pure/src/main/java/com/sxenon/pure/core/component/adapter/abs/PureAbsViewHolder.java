package com.sxenon.pure.core.component.adapter.abs;

import com.sxenon.pure.core.component.adapter.IPureViewHolder;
import com.sxenon.pure.core.mvp.IViewModule;

/**
 * Created by Sui on 2016/12/25.
 */

public abstract class PureAbsViewHolder<T> implements IPureViewHolder<T> {
    private final int position;
    private final PureAbsListAdapter adapter;
    private final IViewModule viewModule;

    public PureAbsViewHolder(IViewModule viewModule,PureAbsListAdapter adapter, int position){
        this.position=position;
        this.adapter=adapter;
        this.viewModule=viewModule;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @SuppressWarnings("unchecked")
    public PureAbsListAdapter getAdapter(){
        return adapter;
    }

    public IViewModule getViewModule() {
        return viewModule;
    }
}
