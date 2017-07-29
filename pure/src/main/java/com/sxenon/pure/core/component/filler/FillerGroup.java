/*
 * Copyright (c) 2017 sxenon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sxenon.pure.core.component.filler;

import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.component.IViewComponentGroup;
import com.sxenon.pure.core.result.IFetchListResultHandler;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;
import com.sxenon.pure.core.util.CommonUtils;
import com.sxenon.pure.core.util.Preconditions;

import java.util.List;

/**
 * Process single,list data together.
 * Created by Sui on 2016/12/8.
 */

public abstract class FillerGroup<R, PL extends IPullLayout> implements IFetchSingleResultHandler<R>, IFetchListResultHandler<R>,IViewComponentGroup{
    private int mCurrentPageCount;
    private int tempPageCount;
    private int eventWhat = EventWhat.WHAT_UNINITIALIZED;
    private ApiException mException;

    private final IPureAdapter<R> mAdapter;
    private final IFetchSingleResultHandler<R> mSingleDataResultHandler;
    private final ListDataFillStrategy<R> mListDataFillStrategy;
    private R mValue;

    private final PL mPullLayout;
    private final Context mContext;
    private final boolean mIsRefreshForAdd;

    private View mEmptyView;
    private View mExceptionView;

    /**
     * Constructor
     *
     * @param pullLayout              刷新容器
     * @param singleDataResultHandler 单一数据的Handler
     */
    public FillerGroup(Context context, PL pullLayout, IFetchSingleResultHandler<R> singleDataResultHandler) {
        this(context, pullLayout, null, singleDataResultHandler, false, null);
    }

    /**
     * Constructor
     *
     * @param pullLayout 刷新容器
     * @param adapter    列表控件相关的adapter
     */
    public FillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter) {
        this(context, pullLayout, adapter, null, false, new DefaultListDataFillStrategy<R>());
    }

    /**
     * Constructor
     *
     * @param pullLayout    刷新容器
     * @param adapter       列表控件相关的adapter
     * @param isFreshForAdd 上拉是添加最新数据还是完整更新，true 表示添加最新数据
     */
    public FillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, boolean isFreshForAdd) {
        this(context, pullLayout, adapter, null, isFreshForAdd, new DefaultListDataFillStrategy<R>());
    }

    /**
     * Constructor
     *
     * @param pullLayout           刷新容器
     * @param adapter              列表控件相关的adapter
     * @param listDataFillStrategy 列表数据填充策略
     */
    public FillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, ListDataFillStrategy<R> listDataFillStrategy) {
        this(context, pullLayout, adapter, null, false, listDataFillStrategy);
    }

    /**
     * Constructor
     *
     * @param pullLayout           刷新容器
     * @param adapter              列表控件相关的adapter
     * @param isFreshForAdd        上拉是添加最新数据还是完整更新，true 表示添加最新数据
     * @param listDataFillStrategy 列表数据填充策略
     */
    public FillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, boolean isFreshForAdd, ListDataFillStrategy<R> listDataFillStrategy) {
        this(context, pullLayout, adapter, null, isFreshForAdd, listDataFillStrategy);
    }

    private FillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, IFetchSingleResultHandler<R> singleDataResultHandler, boolean isFreshForAdd, ListDataFillStrategy<R> listDataFillStrategy) {
        mContext = context;
        mPullLayout = pullLayout;
        mAdapter = adapter;
        mSingleDataResultHandler = singleDataResultHandler;
        mIsRefreshForAdd = isFreshForAdd;
        mListDataFillStrategy = listDataFillStrategy;
    }

    /**
     * For subclass call,see demo
     */
    protected final void onBeginPullingDown() {
        if (mCurrentPageCount == 0) {
            beforeInitializing();
        } else {
            beforePullingDown();
        }
        if (mAdapter!=null){
            if (!mIsRefreshForAdd) {
                tempPageCount = 1;
            } else {
                tempPageCount = mCurrentPageCount;
            }
        }
    }

    /**
     * For subclass call,see demo
     */
    protected final void onBeginPullingUp() {
        beforePullingUp();
        if (mAdapter!=null){
            tempPageCount = mCurrentPageCount + 1;
        }
    }

    public void setExtraComponents(View emptyView, View exceptionView) {
        mEmptyView = emptyView;
        mExceptionView = exceptionView;

        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
    }

    public void resetPageCount() {
        if (mAdapter != null) {
            mAdapter.clearAllItems();
        }
        mCurrentPageCount = tempPageCount = 0;
    }

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

    private void toInitialize() {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                beginPullingDown();
                return false;
            }
        });
    }

    public void restoreEvent(Event savedEvent) {
        if (savedEvent == null) {
            toInitialize();
            return;
        }
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
                toInitialize();
                break;
            case EventWhat.WHAT_NORMAL:
                if (object instanceof List) {
                    //noinspection unchecked
                    Preconditions.checkNotNull(mAdapter, "").resetAllItems((List<R>) object);
                } else {
                    //noinspection unchecked
                    Preconditions.checkNotNull(mSingleDataResultHandler, "").onSingleDataFetched((R) object);
                }
                break;
        }
    }

    public R getValue() {
        Preconditions.checkNotNull(mSingleDataResultHandler, "");
        return mValue;
    }

    public List<R> getValues() {
        return Preconditions.checkNotNull(mAdapter, "").getValues();
    }

    private void endAllAnim() {
        mPullLayout.endPullingUp();
        mPullLayout.endPullingDown();
    }

    protected void onNoMoreData() {

    }

    protected void onNoNewData() {

    }

    protected void onMoreDataFetched(List<R> data) {
        mListDataFillStrategy.onMoreDataFetched(mAdapter,data);
    }

    protected void onNewDataFetched(List<R> data) {
        mListDataFillStrategy.onNewDataFetched(mAdapter,data);
    }

    protected void onInitDataFetched(List<R> data) {
        mListDataFillStrategy.onInitDataFetched(mAdapter,data);
    }

    protected void onEmpty() {
        eventWhat = EventWhat.WHAT_EMPTY;
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
        CommonUtils.setViewVisibility(mEmptyView, View.VISIBLE);
    }

    @Override
    public void onSingleDataFetched(R data) {
        mValue = data;
        Preconditions.checkNotNull(mSingleDataResultHandler, "single data but no singleDataResult!");
        endAllAnim();
        if (data == null) {
            onEmpty();
        } else {
            eventWhat = EventWhat.WHAT_NORMAL;
            mCurrentPageCount = tempPageCount = 1;
            CommonUtils.setViewVisibility(mEmptyView, View.GONE);
            CommonUtils.setViewVisibility(mExceptionView, View.GONE);
            mSingleDataResultHandler.onSingleDataFetched(data);
        }
    }

    @Override
    public void onListDataFetched(List<R> data) {
        eventWhat = EventWhat.WHAT_NORMAL;
        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
        Preconditions.checkNotNull(mAdapter, "list data but no adapter!");
        endAllAnim();
        if (data == null || data.isEmpty()) {
            if (mCurrentPageCount == 0) {
                onEmpty();
            } else if (tempPageCount == mCurrentPageCount) {//refreshForAdd
                onNoNewData();
            } else {
                onNoMoreData();
            }
            tempPageCount = mCurrentPageCount;
        } else {
            if (mIsRefreshForAdd) {
                if (mCurrentPageCount == 0) {
                    onInitDataFetched(data);
                } else if (tempPageCount == mCurrentPageCount) {//refresh
                    onNewDataFetched(data);
                } else {
                    onMoreDataFetched(data);
                }
            } else {
                if (tempPageCount == 1) {
                    onInitDataFetched(data);
                } else {
                    onMoreDataFetched(data);
                }
            }
            mCurrentPageCount = tempPageCount;
        }
    }

    @Override
    public void onCancel() {
        endAllAnim();
        if (mSingleDataResultHandler != null) {
            mSingleDataResultHandler.onCancel();
        }
        mCurrentPageCount = tempPageCount;
    }

    @Override
    public void onException(ApiException exception) {
        eventWhat = EventWhat.WHAT_EXCEPTION;
        mException = exception;
        endAllAnim();
        resetPageCount();
        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.VISIBLE);
        if (mSingleDataResultHandler != null) {
            mSingleDataResultHandler.onException(exception);
        }
    }

    public void beginPullingDown() {
        mPullLayout.beginPullingDown();
    }

    public void beginPullingUp() {
        mPullLayout.beginPullingUp();
    }

    //Getter start
    public PL getPullLayout() {
        return mPullLayout;
    }

    public Context getContext() {
        return mContext;
    }

    public IPureAdapter<R> getAdapter() {
        return mAdapter;
    }

    public View getExceptionView() {
        return mExceptionView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public int getCurrentPageCount() {
        return mCurrentPageCount;
    }

    public int getEventWhat() {
        return eventWhat;
    }

    //Getter end

    //before start
    protected void beforeInitializing() {

    }

    protected void beforePullingDown() {

    }

    protected void beforePullingUp() {

    }
    //before end

    public class EventWhat {
        public static final int WHAT_UNINITIALIZED = 1;
        public static final int WHAT_NORMAL = 2;
        public static final int WHAT_EMPTY = 3;
        public static final int WHAT_EXCEPTION = 4;
    }

}
