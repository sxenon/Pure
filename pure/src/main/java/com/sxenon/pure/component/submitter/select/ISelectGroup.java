package com.sxenon.pure.component.submitter.select;

import com.sxenon.pure.component.submitter.IListDataSubmitter;

import java.util.List;

/**
 * For adapter with select
 * Created by Sui on 2016/11/18.
 */

public interface ISelectGroup<T> extends IListDataSubmitter<T> {
    int SELECT_MODE_SINGLE=0;
    int SELECT_MODE_MULTI=1;

    void addOption(T data);
    void insertOptionAsFirst(T data);
    void removeOption(T data);
    void removeOption(int position);
    void setOptions(List<T> data);
    void onSelectedOption(int position);
    void onUnSelectedOption(int position);
}
