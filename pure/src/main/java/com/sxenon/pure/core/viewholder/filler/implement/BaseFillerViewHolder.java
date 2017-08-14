/*
 * Copyright (c) 2017  sxenon
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

package com.sxenon.pure.core.viewholder.filler.implement;

import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.viewholder.filler.FillEventWhat;
import com.sxenon.pure.core.viewholder.filler.FillPageStrategy;
import com.sxenon.pure.core.viewholder.filler.IFillerViewHolder;
import com.sxenon.pure.core.viewholder.filler.IPullLayout;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;
import com.sxenon.pure.core.util.CommonUtils;
import com.sxenon.pure.core.util.Preconditions;

import java.util.List;

/**
 * Base implement for IFillerViewHolder
 * Created by Sui on 2017/8/4.
 */

public abstract class BaseFillerViewHolder<R, PL extends IPullLayout> implements IFillerViewHolder<R> {
    private final FillPageStrategy.PageInfo mPageInfo = new FillPageStrategy.PageInfo(-1, -1);

    private final IPureAdapter<R> mAdapter;
    private final IFetchSingleResultHandler<R> mSingleDataResultHandler;
    private final FillPageStrategy<R> mFillPageStrategy;
    private final PL mPullLayout;
    private final Context mContext;

    private int mEventWhat = FillEventWhat.WHAT_UNINITIALIZED;
    private ApiException mException;
    private R mValue;

    private View mEmptyView;
    private View mExceptionView;

    /**
     * Constructor
     *
     * @param pullLayout              刷新容器
     * @param singleDataResultHandler 单一数据的Handler
     */
    public BaseFillerViewHolder(Context context, PL pullLayout, IFetchSingleResultHandler<R> singleDataResultHandler, FillPageStrategy<R> fillPageStrategy) {
        this(context, pullLayout, null, singleDataResultHandler, fillPageStrategy);
    }

    /**
     * Constructor
     *
     * @param pullLayout       刷新容器
     * @param adapter          列表控件相关的adapter
     * @param fillPageStrategy 分页数据填充策略
     */
    public BaseFillerViewHolder(Context context, PL pullLayout, IPureAdapter<R> adapter, FillPageStrategy<R> fillPageStrategy) {
        this(context, pullLayout, adapter, null, fillPageStrategy);
    }

    private BaseFillerViewHolder(Context context, PL pullLayout, IPureAdapter<R> adapter, IFetchSingleResultHandler<R> singleDataResultHandler, FillPageStrategy<R> fillPageStrategy) {
        mPullLayout = pullLayout;
        mAdapter = adapter;
        mSingleDataResultHandler = singleDataResultHandler;
        mFillPageStrategy = fillPageStrategy;
        mContext = context;
    }

    public void setExtraComponents(View emptyView, View exceptionView) {
        mEmptyView = emptyView;
        mExceptionView = exceptionView;

        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
    }

    protected void endAllAnim() {
        mPullLayout.endPullingUp();
        mPullLayout.endPullingDown();
    }

    /**
     * For subclass call,see demo
     */
    protected final void onBeginPullingDown() {
        if (FillEventWhat.WHAT_UNINITIALIZED == mEventWhat) {
            mFillPageStrategy.onInitialize(this, mPageInfo);
        } else {
            mFillPageStrategy.onPullDown(this, mPageInfo);
        }
    }

    /**
     * For subclass call,see demo
     */
    protected final void onBeginPullingUp() {
        mFillPageStrategy.onPullUp(this, mPageInfo);
    }

    protected void toInitialize() {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                beginPullingDown();
                return false;
            }
        });
    }

    public void beginPullingDown() {
        mPullLayout.beginPullingDown();
    }

    public void beginPullingUp() {
        mPullLayout.beginPullingUp();
    }

    //Event start
    public Event getCurrentEvent() {
        Event event = new Event();
        event.what = mEventWhat;
        event.arg1 = mPageInfo.currentPage;

        if (event.what == FillEventWhat.WHAT_EXCEPTION) {
            event.obj = mException;
        } else {
            if (getAdapter() != null) {
                event.obj = mAdapter.getValues();
            } else {
                event.obj = mValue;
            }
        }
        return event;
    }

    public void restoreEvent(Event savedEvent) {
        if (savedEvent == null) {
            toInitialize();
            return;
        }
        mPageInfo.currentPage = mPageInfo.tempPage = savedEvent.arg1;
        mEventWhat = savedEvent.what;
        Object object = savedEvent.obj;
        switch (savedEvent.what) {
            case FillEventWhat.WHAT_EMPTY:
                onEmpty();
                break;
            case FillEventWhat.WHAT_EXCEPTION:
                onException((ApiException) savedEvent.obj);
                break;
            case FillEventWhat.WHAT_UNINITIALIZED:
                toInitialize();
                break;
            case FillEventWhat.WHAT_NORMAL:
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
    //Event end

    //Implement start
    @Override
    public void onSingleDataFetched(R data) {
        setValue(data);
        Preconditions.checkNotNull(mSingleDataResultHandler, "single data but no singleDataResult!");
        endAllAnim();
        if (data == null) {
            mFillPageStrategy.onSingleDataEmpty(this, mPageInfo);
        } else {
            mEventWhat = FillEventWhat.WHAT_NORMAL;
            CommonUtils.setViewVisibility(mEmptyView, View.GONE);
            CommonUtils.setViewVisibility(mExceptionView, View.GONE);
            mFillPageStrategy.processSingleData(this, data, mSingleDataResultHandler, mPageInfo);
        }
    }

    @Override
    public void onListDataFetched(List<R> data) {
        mEventWhat = FillEventWhat.WHAT_NORMAL;
        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
        Preconditions.checkNotNull(mAdapter, "list data but no adapter!");
        endAllAnim();
        if (data == null || data.isEmpty()) {
            mFillPageStrategy.onListDataEmpty(this, mPageInfo);
        } else {
            mFillPageStrategy.processListData(this, data, mAdapter, mPageInfo);
        }
    }

    @Override
    public void onCancel() {
        endAllAnim();
        if (mSingleDataResultHandler != null) {
            mSingleDataResultHandler.onCancel();
        }
        mPageInfo.currentPage = mPageInfo.tempPage;
    }

    @Override
    public void onException(ApiException exception) {
        endAllAnim();
        mEventWhat = FillEventWhat.WHAT_EXCEPTION;
        mException = exception;
        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.VISIBLE);
        mFillPageStrategy.onException(this, exception, mAdapter, mSingleDataResultHandler, mPageInfo);
    }

    @Override
    public void onEmpty() {
        mEventWhat = FillEventWhat.WHAT_EMPTY;
        mPageInfo.currentPage = mPageInfo.tempPage = -1;
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
        CommonUtils.setViewVisibility(mEmptyView, View.VISIBLE);
    }
    //Implement end

    //Getter start
    public View getExceptionView() {
        return mExceptionView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public int getFillEventWhat() {
        return mEventWhat;
    }

    public PL getPullLayout() {
        return mPullLayout;
    }

    public Context getContext() {
        return mContext;
    }

    public IPureAdapter<R> getAdapter() {
        return mAdapter;
    }

    public ApiException getException() {
        return mException;
    }

    public IFetchSingleResultHandler<R> getSingleDataResultHandler() {
        return mSingleDataResultHandler;
    }

    public R getValue() {
        Preconditions.checkNotNull(mSingleDataResultHandler, "");
        return mValue;
    }

    public List<R> getValues() {
        return Preconditions.checkNotNull(mAdapter, "").getValues();
    }

    //Getter end

    //Setter start
    protected void setFillerEventWhat(int eventWhat) {
        mEventWhat = eventWhat;
    }

    protected void setException(ApiException exception) {
        mException = exception;
    }

    protected void setValue(R value) {
        mValue = value;
    }

    public int getCurrentPageCount() {
        return mPageInfo.currentPage;
    }
    //Setter end
}
