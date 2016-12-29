package com.sxenon.pure.core.component.adapter.abs;

import com.sxenon.pure.core.component.adapter.IPureViewHolder;
import com.sxenon.pure.core.router.IRouter;

/**
 * Created by Sui on 2016/12/25.
 */

public abstract class PureAbsViewHolder<T> implements IPureViewHolder<T> {
    private final int position;
    private final PureAbsListAdapter adapter;
    private final IRouter router;

    public PureAbsViewHolder(PureAbsListAdapter adapter,int position){
        this.position=position;
        this.adapter=adapter;
        router= (IRouter) this.adapter.getContext();
    }

    @Override
    public int getPosition() {
        return position;
    }

    @SuppressWarnings("unchecked")
    public PureAbsListAdapter getAdapter(){
        return adapter;
    }

    public IRouter getRouter(){
        return router;
    }
}
