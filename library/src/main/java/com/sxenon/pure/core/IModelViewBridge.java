package com.sxenon.pure.core;

import java.util.List;

/**
 * 数据与视图的中间层，例如各种adapter
 * Created by Sui on 2016/11/20.
 */

public interface IModelViewBridge<T> {
    int getItemCount();

    void addItemFromEnd(T value);

    void addItemFromStart(T value);

    void addAllItemFromStart(List<T> value);

    void addAllItemFromEnd(List<T> value);

    void addItem(T value, int position);

    void removeItemFromEnd(T value);

    void removeItemFromStart(T value);

    void removeAllItemFromStart(List<T> value);

    void removeAllItemFromEnd(List<T> value);

    void removeItem(int position);

    void removeItem(T value);

    List<T> getValues();
}
