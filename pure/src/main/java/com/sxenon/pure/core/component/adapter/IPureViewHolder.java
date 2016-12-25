package com.sxenon.pure.core.component.adapter;

/**
 * Created by Sui on 2016/12/25.
 */

public interface IPureViewHolder<T> {
    int getPosition();
    void fillViewByData(T data);
}
