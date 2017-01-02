package com.sxenon.pure.core.component.adapter.rv;

import com.sxenon.pure.core.component.adapter.IItemViewTypeEntity;

/**
 * Created by Sui on 2016/12/29.
 */

public class PureRecyclerViewItemViewTypeEntity implements IItemViewTypeEntity {
    private final int resourceId;
    private final Class<? extends PureRecyclerViewHolder> viewHolderClass;

    public PureRecyclerViewItemViewTypeEntity(int resourceId, Class<? extends PureRecyclerViewHolder> viewHolderClass) {
        this.resourceId = resourceId;
        this.viewHolderClass = viewHolderClass;
    }

    public int getResourceId() {
        return resourceId;
    }

    public Class<? extends PureRecyclerViewHolder> getViewHolderClass() {
        return viewHolderClass;
    }
}
