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

package com.sxenon.pure.core.viewholder.filler;

import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.util.CommonUtils;
import com.sxenon.pure.core.viewholder.ILoadingView;

/**
 * Base implement for IFillerViewHolder
 * Created by Sui on 2017/8/4.
 */

public abstract class BaseFillerViewHolder<PL extends IPullLayout,S extends IFillPageStrategy> implements IFillerViewHolder {
    private final IFillPageStrategy.PageInfo pageInfo = new IFillPageStrategy.PageInfo(-1, -1);

    private final S mFillPageStrategy;
    private final PL mPullLayout;
    private final Context mContext;

    private int mEventWhat = FillEventWhat.WHAT_UNINITIALIZED;
    private ApiException mApiException;

    private View mEmptyView;
    private View mExceptionView;
    private ILoadingView mLoadingView;

    private OnFillEventListener mOnFillEventListener;

    /**
     * Constructor
     *
     * @param context          上下文
     * @param pullLayout       刷新容器
     * @param fillPageStrategy 分页数据填充策略
     */
    public BaseFillerViewHolder(Context context, PL pullLayout, S fillPageStrategy) {
        mPullLayout = pullLayout;
        mFillPageStrategy = fillPageStrategy;
        mContext = context;
    }

    public BaseFillerViewHolder setExtraComponents(View emptyView, View exceptionView) {
        mEmptyView = emptyView;
        mExceptionView = exceptionView;

        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
        return this;
    }

    public BaseFillerViewHolder setLoadingView(ILoadingView loadingView){
        mLoadingView = loadingView;
        return this;
    }

    public void endAllAnim() {
        mPullLayout.endPullingUp();
        mPullLayout.endPullingDown();
        if(mLoadingView!=null){
            mLoadingView.hideLoading();
        }
    }

    /**
     * For subclass call,see demo
     */
    protected final void onBeginPullingDown() {
        mFillPageStrategy.onPullDown(pageInfo);
    }

    /**
     * For subclass call,see demo
     */
    protected final void onBeginPullingUp() {
        mFillPageStrategy.onPullUp(pageInfo);
    }

    public void toInitialize() {
        beginPullingDown();
    }

    public void beginPullingDown() {
        mPullLayout.beginPullingDown();
        if (mLoadingView!=null){
            mLoadingView.showLoading();
        }
    }

    public void beginPullingUp() {
        mPullLayout.beginPullingUp();
        if (mLoadingView!=null){
            mLoadingView.showLoading();
        }
    }

    //Event start
    public Event getCurrentEvent() {
        Event event = new Event();
        event.what = mEventWhat;
        event.arg1 = pageInfo.currentPage;

        if (event.what == FillEventWhat.WHAT_EXCEPTION) {
            event.obj = mApiException;
        } else {
            event.obj = getData();
        }
        return event;
    }

    public void restoreEvent(Event savedEvent) {
        if (savedEvent == null) {
            toInitialize();
            return;
        }
        pageInfo.currentPage = pageInfo.tempPage = savedEvent.arg1;
        mEventWhat = savedEvent.what;
        switch (savedEvent.what) {
            case FillEventWhat.WHAT_EMPTY:
                onEmpty();
                break;
            case FillEventWhat.WHAT_EXCEPTION:
                onApiException((ApiException) savedEvent.obj);
                break;
            case FillEventWhat.WHAT_UNINITIALIZED:
                toInitialize();
                break;
            case FillEventWhat.WHAT_NON_EMPTY:
                restoreData(savedEvent.obj);
                break;
        }
    }
    //Event end

    //Implement start
    @Override
    public void onNonEmpty() {
        mEventWhat = FillEventWhat.WHAT_NON_EMPTY;
        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
    }

    @Override
    public void onCancel() {
        endAllAnim();
        pageInfo.tempPage = pageInfo.currentPage;
        if (mOnFillEventListener!=null){
            mOnFillEventListener.onCancel();
        }
    }

    @Override
    public void onApiException(ApiException apiException) {
        endAllAnim();
        mEventWhat = FillEventWhat.WHAT_EXCEPTION;
        mApiException = apiException;
        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.VISIBLE);
        if (mOnFillEventListener!=null){
            mOnFillEventListener.onApiException(apiException);
        }
    }

    @Override
    public void onEmpty() {
        mEventWhat = FillEventWhat.WHAT_EMPTY;
        pageInfo.currentPage = pageInfo.tempPage = -1;
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
        CommonUtils.setViewVisibility(mEmptyView, View.VISIBLE);
        if (mOnFillEventListener!=null){
            mOnFillEventListener.onEmpty();
        }
    }
    //Implement end


    public void setOnFillEventListener(OnFillEventListener mOnFillEventListener) {
        this.mOnFillEventListener = mOnFillEventListener;
    }

    public interface OnFillEventListener{
        void onEmpty();
        void onApiException(ApiException apiException);
        void onCancel();
    }

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

    @Override
    public Context getContext() {
        return mContext;
    }

    public ApiException getException() {
        return mApiException;
    }

    public S getFillPageStrategy() {
        return mFillPageStrategy;
    }

    public abstract Object getData();
    //Getter end

    //Setter start
    public abstract void restoreData(Object data);

    public void setFillerEventWhat(int eventWhat) {
        mEventWhat = eventWhat;
    }

    public void setException(ApiException exception) {
        mApiException = exception;
    }

    public int getCurrentPageCount() {
        return pageInfo.currentPage;
    }

    public IFillPageStrategy.PageInfo getPageInfo() {
        return pageInfo;
    }
    //Setter end
}
