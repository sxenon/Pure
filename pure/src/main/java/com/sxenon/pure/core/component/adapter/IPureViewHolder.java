package com.sxenon.pure.core.component.adapter;

import com.sxenon.pure.core.mvp.IViewModule;

/**
 * Created by Sui on 2016/12/25.
 */

public interface IPureViewHolder<T> {
    int getPosition();

    void fillViewByData(T data);

    <A extends IPureAdapter> A getAdapter();

    IViewModule getViewModule();
}
