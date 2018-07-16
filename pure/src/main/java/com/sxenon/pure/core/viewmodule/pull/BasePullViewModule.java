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

package com.sxenon.pure.core.viewmodule.pull;

import android.content.Context;
import android.view.View;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.util.CommonUtils;
import com.sxenon.pure.core.viewmodule.ILoadingView;

/**
 * Base implement for IPullViewModule
 * Created by Sui on 2017/8/4.
 */

public abstract class BasePullViewModule<PL extends IPullLayout,S extends IPullStrategy> implements IPullViewModule {
    private final IPullStrategy.PageInfo pageInfo = new IPullStrategy.PageInfo(-1, -1);

    private final S mPullStrategy;
    private final PL mPullLayout;
    private final Context mContext;

    private int mEventWhat = PullEventWhat.WHAT_UNINITIALIZED;
    private ApiException mApiException;

    private View mEmptyView;
    private View mExceptionView;
    private ILoadingView mLoadingView;

    private EventListener mEventListener;

    /**
     * Constructor
     *
     * @param context          上下文
     * @param pullLayout       刷新容器
     * @param pullStrategy 分页数据填充策略
     */
    public BasePullViewModule(Context context, PL pullLayout, S pullStrategy) {
        mPullLayout = pullLayout;
        mPullStrategy = pullStrategy;
        mContext = context;
    }

    public BasePullViewModule setExtraComponents(View emptyView, View exceptionView) {
        mEmptyView = emptyView;
        mExceptionView = exceptionView;

        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
        return this;
    }

    public BasePullViewModule setLoadingView(ILoadingView loadingView){
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
        mPullStrategy.onPullDown(pageInfo);
    }

    /**
     * For subclass call,see demo
     */
    protected final void onBeginPullingUp() {
        mPullStrategy.onPullUp(pageInfo);
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

        if (event.what == PullEventWhat.WHAT_EXCEPTION) {
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
            case PullEventWhat.WHAT_EMPTY:
                onEmpty();
                break;
            case PullEventWhat.WHAT_EXCEPTION:
                onApiException((ApiException) savedEvent.obj);
                break;
            case PullEventWhat.WHAT_UNINITIALIZED:
                toInitialize();
                break;
            case PullEventWhat.WHAT_NON_EMPTY:
                restoreData(savedEvent.obj);
                break;
        }
    }
    //Event end

    //Implement start
    @Override
    public void onNonEmpty() {
        mEventWhat = PullEventWhat.WHAT_NON_EMPTY;
        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
    }

    @Override
    public void onCancel() {
        endAllAnim();
        pageInfo.tempPage = pageInfo.currentPage;
        if ( mEventListener !=null){
            mEventListener.onCancel();
        }
    }

    @Override
    public void onApiException(ApiException apiException) {
        endAllAnim();
        mEventWhat = PullEventWhat.WHAT_EXCEPTION;
        mApiException = apiException;
        CommonUtils.setViewVisibility(mEmptyView, View.GONE);
        CommonUtils.setViewVisibility(mExceptionView, View.VISIBLE);
        if ( mEventListener !=null){
            mEventListener.onApiException(apiException);
        }
    }

    @Override
    public void onEmpty() {
        mEventWhat = PullEventWhat.WHAT_EMPTY;
        pageInfo.currentPage = pageInfo.tempPage = -1;
        CommonUtils.setViewVisibility(mExceptionView, View.GONE);
        CommonUtils.setViewVisibility(mEmptyView, View.VISIBLE);
        if ( mEventListener !=null){
            mEventListener.onEmpty();
        }
    }
    //Implement end


    public void setEventListener(EventListener eventListener) {
        this.mEventListener = eventListener;
    }

    public interface EventListener {
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

    public int getPullEventWhat() {
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

    public S getPullStrategy() {
        return mPullStrategy;
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

    public IPullStrategy.PageInfo getPageInfo() {
        return pageInfo;
    }
    //Setter end
}
