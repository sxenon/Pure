package com.sxenon.pure.core;

import java.util.List;

/**
 * 数据与视图的中间层，例如各种adapter
 * Created by Sui on 2016/11/20.
 */

public interface IPureAdapter<T> {
    int getItemCount();

    void addItemFromEnd(T value);

    void addItemFromStart(T value);

    void addItemsFromStart(List<T> value);

    void addItemsFromEnd(List<T> value);

    void addItem(T value, int position);

    void removeItems(List<T> value);

    void removeItem(int position);

    void removeItem(T value);

    List<T> getValues();

    void resetAllItems(List<T> values);

    void clear();
}