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

package com.sxenon.pure.core.component.filler.implement;

import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.component.filler.FillEventWhat;
import com.sxenon.pure.core.component.filler.IFillerGroup;
import com.sxenon.pure.core.component.filler.IPullLayout;
import com.sxenon.pure.core.component.filler.ListDataFillStrategy;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;
import com.sxenon.pure.core.util.CommonUtils;
import com.sxenon.pure.core.util.Preconditions;

import java.util.List;

/**
 * Base implement for IFillerGroup
 * Created by Sui on 2017/8/4.
 */

public abstract class BaseFillerGroup<R, PL extends IPullLayout> implements IFillerGroup<R> {
    protected int mCurrentPageCount;
    protected int tempPageCount;

    private final IPureAdapter<R> mAdapter;
    private final IFetchSingleResultHandler<R> mSingleDataResultHandler;
    private final ListDataFillStrategy<R> mListDataFillStrategy;
    private final PL mPullLayout;
    private final Context mContext;

    private int mEventWhat = FillEventWhat.WHAT_UNINITIALIZED;
    private ApiException mException;
    private R mValue;

    private View mEmptyView;
    private View mExceptionView;

    protected BaseFillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, IFetchSingleResultHandler<R> singleDataResultHandler, ListDataFillStrategy<R> listDataFillStrategy){
        mPullLayout=pullLayout;
        mAdapter=adapter;
        mSingleDataResultHandler=singleDataResultHandler;
        mListDataFillStrategy=listDataFillStrategy;
        mContext=context;
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

    //before start
    protected void beforeInitializing() {

    }

    protected void beforePullingDown() {

    }

    protected void beforePullingUp() {

    }
    //before end

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

    public void resetPageCount() {
        if (getAdapter() != null) {
            getAdapter().clearAllItems();
        }
        mCurrentPageCount = tempPageCount = 0;
    }

    //Event start
    public Event getCurrentEvent() {
        Event event = new Event();
        event.what = mEventWhat;
        event.arg1 = mCurrentPageCount;

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
        mCurrentPageCount = tempPageCount = savedEvent.arg1;
        mEventWhat=savedEvent.what;
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

    public ApiException getException(){
        return mException;
    }

    public IFetchSingleResultHandler<R> getSingleDataResultHandler(){
        return mSingleDataResultHandler;
    }

    public R getValue() {
        Preconditions.checkNotNull(mSingleDataResultHandler, "");
        return mValue;
    }

    public List<R> getValues() {
        return Preconditions.checkNotNull(mAdapter, "").getValues();
    }

    public ListDataFillStrategy<R> getListDataFillStrategy(){
        return mListDataFillStrategy;
    }
    //Getter end

    //Setter start
    protected void setFillerEventWhat(int eventWhat){
        mEventWhat =eventWhat;
    }

    protected void setException(ApiException exception){
        mException=exception;
    }

    protected void setValue(R value){
        mValue=value;
    }

    public int getCurrentPageCount() {
        return mCurrentPageCount;
    }
    //Setter end
}
