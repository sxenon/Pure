package com.sxenon.pure.core.component.adapter.abs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.sxenon.pure.core.component.adapter.IPureAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sui on 2016/12/25.
 */

public abstract class PureAbsListAdapter<T> extends ArrayAdapter<T> implements IPureAdapter<T> {

    private final Object mLock;
    private final Field mOriginalValuesField;
    private final Field mObjectsField;
    private final Field mNotifyOnChangeField;
    private final List<PureAbsListItemViewTypeEntity> mItemViewTypeEntryList;

    public PureAbsListAdapter(Context context, int resource,@NonNull List<PureAbsListItemViewTypeEntity> itemViewTypeEntryList) {
        super(context, resource);
        mItemViewTypeEntryList=itemViewTypeEntryList;
    }

    public PureAbsListAdapter(Context context, int resource, T[] objects,@NonNull List<PureAbsListItemViewTypeEntity> itemViewTypeEntryList) {
        super(context, resource, objects);
        mItemViewTypeEntryList=itemViewTypeEntryList;
    }

    public PureAbsListAdapter(Context context, int resource, List<T> objects,@NonNull List<PureAbsListItemViewTypeEntity> itemViewTypeEntryList) {
        super(context, resource, objects);
        mItemViewTypeEntryList=itemViewTypeEntryList;
    }

    {
        Object lock = null;
        Field originalValuesField = null;
        Field objectsField = null;
        Field notifyOnChangeField = null;
        Class superClass = getClass().getSuperclass();
        try {
            Field lockField = superClass.getDeclaredField("mLock");
            lockField.setAccessible(true);
            lock = lockField.get(this);

            originalValuesField = superClass.getDeclaredField("mOriginalValues");
            originalValuesField.setAccessible(true);

            objectsField = superClass.getDeclaredField("mObjects");
            objectsField.setAccessible(true);

            notifyOnChangeField = superClass.getDeclaredField("mNotifyOnChange");
            notifyOnChangeField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLock = lock;
        mOriginalValuesField = originalValuesField;
        mObjectsField = objectsField;
        mNotifyOnChangeField = notifyOnChangeField;
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    @Override
    public void addItemFromEnd(T value) {
        addItem(value,getItemCount());
    }

    @Override
    public void addItemFromStart(T value) {
        addItem(value, 0);
    }

    @Override
    public void addItemsFromStart(List<T> values) {
        addItems(values, 0);
    }

    @Override
    public void addItemsFromEnd(List<T> values) {
        addItems(values, getItemCount());
    }

    @Override
    public void addItem(T value, int position) {
        if (position < 0 || position > getCount() || value == null) {
            return;
        }
        insert(value, position);
    }

    @Override
    public void addItems(List<T> values, int position) {
        if (position < 0 || position > getCount() || values == null) {
            return;
        }
        synchronized (mLock) {
            getValues().addAll(position, values);
        }
        if (isNotifyOnChange()) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void removeItems(List<T> values) {
        synchronized (mLock) {
            getValues().removeAll(values);
        }
        if (isNotifyOnChange()) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void removeItems(int position, int count) {
        if (position < 0 || count < 1 || position + count > getCount()) {
            return;
        }
        removeItems(getValues().subList(position, position + count));
    }

    @Override
    public void removeItem(int position) {
        remove(getValue(position));
    }

    @Override
    public void removeItem(T value) {
        remove(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getValues() {
        List<T> originalValues = null;
        List<T> objects = null;
        try {
            originalValues = (List<T>) mOriginalValuesField.get(this);
            objects = (List<T>) mObjectsField.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return originalValues != null ? originalValues : objects;
    }

    @Override
    public T getValue(int position) {
        if (position < 0 || position >= getCount()) {
            return null;
        }
        return getItem(position);
    }

    @Override
    public void resetAllItems(List<T> values) {
        if (values == null) {
            clearAllItems();
        } else {
            synchronized (mLock) {
                getValues().clear();
                getValues().addAll(values);
            }
            if (isNotifyOnChange()) {
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void clearAllItems() {
        clear();
    }

    @Override
    public void setItem(int position, T value) {
        if (value == null || position < 0 || position >= getCount()) {
            return;
        }
        synchronized (mLock) {
            getValues().set(position, value);
        }
        if (isNotifyOnChange()) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void invalidate(T oldValue, T newValue) {
        setItem(getPosition(oldValue), newValue);
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {
        if (fromPosition < 0 || fromPosition >= getCount() || toPosition < 0 || toPosition >= getCount()) {
            return;
        }
        if (fromPosition == toPosition) {
            return;
        }
        synchronized (mLock) {
            Collections.swap(getValues(), fromPosition, toPosition);
        }
        if (isNotifyOnChange()) {
            notifyDataSetChanged();
        }
    }

    private boolean isNotifyOnChange() {
        boolean isNotifyOnChange = true;
        try {
            isNotifyOnChange = mNotifyOnChangeField.getBoolean(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return isNotifyOnChange;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        PureAbsViewHolder viewHolder=null;
        if (convertView==null){
            PureAbsListItemViewTypeEntity itemViewTypeEntity=mItemViewTypeEntryList.get(getItemViewType(position));
            convertView= LayoutInflater.from(getContext()).inflate(itemViewTypeEntity.getResourceId(),null);
            Class<? extends PureAbsViewHolder> viewHolderClass=itemViewTypeEntity.getViewHolderClass();
            try {
                Constructor<? extends PureAbsViewHolder> constructor=viewHolderClass.getConstructor(Integer.class);
                viewHolder=constructor.newInstance(position);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (PureAbsViewHolder) convertView.getTag();
        }
        //noinspection ConstantConditions,unchecked
        viewHolder.fillViewByData(getItem(position));
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return mItemViewTypeEntryList.size();
    }

    protected List<PureAbsListItemViewTypeEntity> getItemViewTypeEntryList(){
        return mItemViewTypeEntryList;
    }
    /**
     * You must override it!
     */
    @Override
    public abstract int getItemViewType(int position);
}
