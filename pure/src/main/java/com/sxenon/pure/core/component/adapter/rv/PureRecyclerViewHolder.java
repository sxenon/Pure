package com.sxenon.pure.core.component.adapter.rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sxenon.pure.core.component.adapter.IPureViewHolder;
import com.sxenon.pure.core.mvp.IViewModule;

/**
 * Created by Sui on 2016/12/29.
 */

public abstract class PureRecyclerViewHolder<T> extends RecyclerView.ViewHolder implements IPureViewHolder<T> {
    private final PureRecyclerViewAdapter adapter;
    private final IViewModule viewModule;

    public PureRecyclerViewHolder(IViewModule viewModule,View itemView, PureRecyclerViewAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
        this.viewModule=viewModule;
    }

    @SuppressWarnings("unchecked")
    public PureRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    public IViewModule getViewModule() {
        return viewModule;
    }
}
