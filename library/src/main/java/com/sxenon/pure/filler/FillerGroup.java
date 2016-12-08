package com.sxenon.pure.filler;

import android.os.Looper;
import android.os.MessageQueue;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.IPureAdapter;
import com.sxenon.pure.util.Preconditions;

import java.util.List;

/**
 * Process single,list data together.
 * Created by Sui on 2016/12/8.
 */

public abstract class FillerGroup<T> implements ISingleDataFiller<T>, IListDataFiller<T>, ISingleDataResult<T>, IListDataResult<T> {
    private int mCurrentPageCount;
    private int tempPageCount;
    private int eventWhat = EventWhat.WHAT_UNINITIALIZED;
    private ApiException mException;

    private T mValue;

    private IPureAdapter<T> mAdapter;
    private ISingleDataResult<T> mSingleDataResult;

    public Event getCurrentEvent() {
        Event event = new Event();
        event.what = eventWhat;
        event.arg1 = mCurrentPageCount;

        if (eventWhat == EventWhat.WHAT_EXCEPTION) {
            event.obj = mException;
        } else {
            if (mAdapter != null) {
                event.obj = getValues();
            } else {
                event.obj = getValue();
            }
        }
        return event;
    }

    public void restoreEvent(Event savedEvent) {
        mCurrentPageCount = tempPageCount = savedEvent.arg1;
        eventWhat = savedEvent.what;
        Object object = savedEvent.obj;

        switch (eventWhat) {
            case EventWhat.WHAT_EMPTY:
                onEmpty();
                break;
            case EventWhat.WHAT_EXCEPTION:
                onException((ApiException) savedEvent.obj);
                break;
            case EventWhat.WHAT_UNINITIALIZED:
                Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                    @Override
                    public boolean queueIdle() {
                        beginRefreshing();
                        return false;
                    }
                });
                break;
            case EventWhat.WHAT_NORMAL:
                if (object instanceof List) {
                    Preconditions.checkNotNull(mAdapter, "");
                    //noinspection unchecked
                    mAdapter.resetAllItems((List<T>) object);
                } else {
                    Preconditions.checkNotNull(mSingleDataResult, "");
                    //noinspection unchecked
                    mSingleDataResult.onSingleDataFetched((T) object);
                }
                break;
        }
    }

    @Override
    public T getValue() {
        Preconditions.checkNotNull(mSingleDataResult, "");
        return mValue;
    }

    @Override
    public List<T> getValues() {
        Preconditions.checkNotNull(mAdapter, "");
        return mAdapter.getValues();
    }

    @Override
    public void onSingleDataFetched(T value) {
        mValue = value;
        eventWhat = EventWhat.WHAT_NORMAL;
        mSingleDataResult.onSingleDataFetched(value);
    }

    @Override
    public void onEmpty() {
        eventWhat = EventWhat.WHAT_EMPTY;
    }

    @Override
    public void onListDataFetched(List<T> data) {
        eventWhat = EventWhat.WHAT_NORMAL;
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onException(ApiException exception) {
        eventWhat = EventWhat.WHAT_EXCEPTION;
        mException = exception;
    }

    public void beginRefreshing() {

    }

    public void beginLoadingMore() {

    }

    public class EventWhat {
        public static final int WHAT_UNINITIALIZED = 1;
        public static final int WHAT_NORMAL = 2;
        public static final int WHAT_EMPTY = 3;
        public static final int WHAT_EXCEPTION = 4;
    }
}
