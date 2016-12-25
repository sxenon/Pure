package com.sxenon.pure.core.component.adapter;

import java.util.List;

/**
 * adapter
 * Created by Sui on 2016/11/20.
 */

public interface IPureAdapter<T> {
    int getItemCount();

    void addItemFromEnd(T value);

    void addItemFromStart(T value);

    void addItemsFromStart(List<T> values);

    void addItemsFromEnd(List<T> values);

    void addItem(T value, int position);

    void addItems(List<T> values, int position);

    void removeItems(List<T> values);

    void removeItem(int position);

    void removeItem(T value);

    void removeItems(int position, int count);

    List<T> getValues();

    T getValue(int position);

    void resetAllItems(List<T> values);

    void clearAllItems();

    void setItem(int position, T value);

    void invalidate(T oldValue, T newValue);

    void moveItem(int fromPosition, int toPosition);
}
