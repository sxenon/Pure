package com.sxenon.pure.submitter.select;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.IPureAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base impl for ISelectGroup
 * Created by Sui on 2016/11/18.
 */

public abstract class BaseSelectGroup<T> implements ISelectGroup<T> {
    private Context mContext;
    private IPureAdapter<T> mAdapter;
    private List<Boolean> selectedFlags;
    private int mSelectMode;

    /**
     * @param selectMode {@link ISelectGroup#SELECT_MODE_MULTI} or {@link ISelectGroup#SELECT_MODE_SINGLE}
     */
    public BaseSelectGroup(@NonNull Context context, @NonNull IPureAdapter<T> adapter, int selectMode) {
        if (selectMode!=SELECT_MODE_MULTI&&selectMode!=SELECT_MODE_SINGLE){
            throw new IllegalArgumentException("Check the mode please!");
        }
        mContext = context;
        mAdapter = adapter;
        selectedFlags = new ArrayList<>(mAdapter.getItemCount());
        mSelectMode=selectMode;
        Collections.fill(selectedFlags, false);
        SelectGroupHelper.bindAdapterAndSelectGroup(adapter, this);
    }

    @Override
    public void addOption(T data) {
        mAdapter.addItemFromEnd(data);
        selectedFlags.add(false);
    }

    @Override
    public void insertOptionAsFirst(T data) {
        mAdapter.addItemFromStart(data);
        selectedFlags.add(0, false);
    }

    @Override
    public void removeOption(T data) {
        int position = mAdapter.getValues().indexOf(data);
        removeOption(position);
    }

    @Override
    public void removeOption(int position) {
        mAdapter.removeItem(position);
        selectedFlags.remove(position);
    }

    @Override
    public void setOptions(List<T> data) {
        mAdapter.resetAllItems(data);
        selectedFlags = new ArrayList<>(data == null ? 0 : data.size());
        Collections.fill(selectedFlags, false);
    }

    @Override
    public void onSelectedOption(int position) {
        if (selectedFlags.get(position)) {
            return;
        }

        if (mSelectMode == ISelectGroup.SELECT_MODE_MULTI) {
            selectedFlags.set(position, true);
        } else {
            for (int index = 0; index < selectedFlags.size(); index++) {
                if (index != position) {
                    selectedFlags.set(position, false);
                } else {
                    selectedFlags.set(index, true);
                }
            }
        }
    }

    @Override
    public void onUnSelectedOption(int position) {
        selectedFlags.set(position, false);
    }

    @Override
    public List<T> getDataForSubmit() {
        List<T> selectedOptionList = new ArrayList<>();
        for (int position = 0; position < selectedFlags.size(); position++) {
            if (selectedFlags.get(position)) {
                selectedOptionList.add(mAdapter.getValues().get(position));
            }
        }
        return selectedOptionList;
    }

    public List<Integer> getSelectedIndexList() {
        List<Integer> indexList = new ArrayList<>();
        for (int position = 0; position < selectedFlags.size(); position++) {
            if (selectedFlags.get(position)) {
                indexList.add(position);
            }
        }
        return indexList;
    }

    @NonNull
    public Context getContext() {
        return mContext;
    }

    public int getSelectMode() {
        return mSelectMode;
    }

    public IPureAdapter<T> getAdapter() {
        return mAdapter;
    }

    public List<Boolean> getSelectedFlags() {
        return selectedFlags;
    }

}
