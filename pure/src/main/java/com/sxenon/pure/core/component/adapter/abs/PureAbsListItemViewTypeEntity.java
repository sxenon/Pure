package com.sxenon.pure.core.component.adapter.abs;

import com.sxenon.pure.core.component.adapter.IItemViewTypeEntity;

/**
 * itemViewType --- resourceId --- viewHolder
 * Created by Sui on 2016/12/25.
 */

public class PureAbsListItemViewTypeEntity implements IItemViewTypeEntity {
    private final int resourceId;
    private final Class<? extends PureAbsViewHolder> viewHolderClass;

    public PureAbsListItemViewTypeEntity(int resourceId, Class<? extends PureAbsViewHolder> viewHolderClass) {
        this.resourceId = resourceId;
        this.viewHolderClass = viewHolderClass;
    }

    public Class<? extends PureAbsViewHolder> getViewHolderClass() {
        return viewHolderClass;
    }

    public int getResourceId() {
        return resourceId;
    }
}
