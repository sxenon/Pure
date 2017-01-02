package com.sxenon.pure.core.component.adapter;

/**
 * Created by Sui on 2017/1/2.
 */

public interface IItemViewTypeEntity {
    Class<? extends IPureViewHolder> getViewHolderClass();

    int getResourceId();
}
