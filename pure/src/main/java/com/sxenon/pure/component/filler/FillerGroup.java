package com.sxenon.pure.component.filler;

import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.component.adapter.IPureAdapter;
import com.sxenon.pure.router.IRouter;
import com.sxenon.pure.component.filler.pull.BasePullLayout;
import com.sxenon.pure.component.filler.pull.IBasePullLayout;
import com.sxenon.pure.util.Preconditions;

import java.util.List;

import rx.functions.Action1;

/**
 * Process single,list data together.
 * Created by Sui on 2016/12/8.
 */

public abstract class FillerGroup<R, PL extends BasePullLayout> implements ISingleDataFiller<R>, IListDataFiller<R>, IFetchSingleResultHandler<R>, IFetchListResultHandler<R> {
    private int mCurrentPageCount;
    private int tempPageCount;
    private int eventWhat = EventWhat.WHAT_UNINITIALIZED;
    private ApiException mException;

    private final IPureAdapter<R> mAdapter;
    private final IFetchSingleResultHandler<R> mSingleDataResult;
    private R mValue;

    private final PL mPullLayout;
    private final Context mContext;
    private final IRouter mRouter;
    private final boolean mIsRefreshForAdd;

    private View mEmptyView;
    private View mExceptionView;
    private View mClickToRefreshView;

    public FillerGroup(IRouter router, PL pullLayout, IFetchSingleResultHandler<R> singleDataResult) {
        this(router, pullLayout, null, singleDataResult, false);
    }

    public FillerGroup(IRouter router, PL pullLayout, IPureAdapter<R> adapter) {
        this(router, pullLayout, adapter, null, false);
    }

    public FillerGroup(IRouter router, PL pullLayout, IPureAdapter<R> adapter, boolean isFreshForAdd) {
        this(router, pullLayout, adapter, null, isFreshForAdd);
    }

    private FillerGroup(IRouter router, PL pullLayout, IPureAdapter<R> adapter, IFetchSingleResultHandler<R> singleDataResult, boolean isFreshForAdd) {
        mRouter = router;
        mContext = router.getActivityCompact();
        mPullLayout = pullLayout;
        mAdapter = adapter;
        mSingleDataResult = singleDataResult;
        mIsRefreshForAdd = isFreshForAdd;
    }

    public void setBasePullDelegate(final IBasePullLayout.PullDelegate delegate) {
        mPullLayout.setDelegate(new IBasePullLayout.PullDelegate() {
            @Override
            public void onBeginRefreshing() {
                if (mCurrentPageCount == 0) {
                    beforeInitializing();
                } else {
                    beforeRefreshing();
                }
                if (!mIsRefreshForAdd) {
                    tempPageCount = 1;
                } else {
                    tempPageCount = mCurrentPageCount;
                }
                delegate.onBeginRefreshing();
            }

            @Override
            public boolean onBeginLoadingMore() {
                beforeLoadingMore();
                tempPageCount = mCurrentPageCount + 1;
                return delegate.onBeginLoadingMore();
            }
        });
    }

    public void setMinorComponents(View emptyView, View exceptionView, View clickToRefreshView) {
        mEmptyView = emptyView;
        mExceptionView = exceptionView;
        mClickToRefreshView = clickToRefreshView;

        resetMinorComponents();
        if (mClickToRefreshView != null) {
            mRouter.getRootPresenter().getViewBinder().bindViewClickButEmitOnlyFirst(mClickToRefreshView, new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    beginRefreshing();
                }
            });
        }
    }

    private void resetAllComponents() {
        if (mAdapter != null) {
            mAdapter.clear();
        }
        mCurrentPageCount = tempPageCount = 0;
        resetMinorComponents();
    }

    private void resetMinorComponents() {
        if (mClickToRefreshView != null) {
            mClickToRefreshView.setVisibility(View.GONE);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
        if (mExceptionView != null) {
            mExceptionView.setVisibility(View.GONE);
        }
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
                    //noinspection unchecked
                    Preconditions.checkNotNull(mAdapter, "").resetAllItems((List<R>) object);
                } else {
                    //noinspection unchecked
                    Preconditions.checkNotNull(mSingleDataResult, "").onSingleDataFetched((R) object);
                }
                break;
        }
    }

    @Override
    public R getValue() {
        Preconditions.checkNotNull(mSingleDataResult, "");
        return mValue;
    }

    @Override
    public List<R> getValues() {
        return Preconditions.checkNotNull(mAdapter, "").getValues();
    }

    private void endAllAnim() {
        mPullLayout.endLoadingMore();
        mPullLayout.endRefreshing();
    }

    protected void onNoMoreData() {

    }

    protected void onNoNewData() {

    }
    
    protected void onMoreDataFetched(List<R> data) {
        mAdapter.addItemsFromEnd(data);
    }

    protected void onNewDataFetched(List<R> data) {
        mAdapter.addItemsFromStart(data);
    }

    protected void onInitDataFetched(List<R> data) {
        mAdapter.resetAllItems(data);
    }

    @Override
    public void onEmpty() {
        eventWhat = EventWhat.WHAT_EMPTY;
        resetAllComponents();
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSingleDataFetched(R data) {
        mValue = data;
        Preconditions.checkNotNull(mSingleDataResult, "single data but no singleDataResult!");
        endAllAnim();
        if (data == null) {
            onEmpty();
        } else {
            eventWhat = EventWhat.WHAT_NORMAL;
            mCurrentPageCount = tempPageCount = 1;
            onDataFetched();
            resetMinorComponents();
            mSingleDataResult.onSingleDataFetched(data);
        }
    }

    @Override
    public void onListDataFetched(List<R> data) {
        eventWhat = EventWhat.WHAT_NORMAL;
        resetMinorComponents();
        onDataFetched();
        Preconditions.checkNotNull(mAdapter, "list data but no adapter!");
        endAllAnim();
        if (data == null || data.isEmpty()) {
            if (mCurrentPageCount == 0) {
                onEmpty();
            } else if (tempPageCount == mCurrentPageCount) {//mIsRefreshForAdd and refresh
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
        if (mSingleDataResult != null) {
            mSingleDataResult.onCancel();
        }
        mCurrentPageCount = tempPageCount;
    }

    @Override
    public void onException(ApiException exception) {
        eventWhat = EventWhat.WHAT_EXCEPTION;
        mException = exception;
        endAllAnim();
        resetAllComponents();
        if (mExceptionView != null) {
            mExceptionView.setVisibility(View.VISIBLE);
        }
        if (mClickToRefreshView != null) {
            mClickToRefreshView.setVisibility(View.VISIBLE);
        }
        if (mSingleDataResult != null) {
            mSingleDataResult.onException(exception);
        }
    }

    public void beginRefreshing() {
        mPullLayout.beginRefreshing();
    }

    public void beginLoadingMore() {
        mPullLayout.beginLoadingMore();
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

    public View getClickToRefreshView() {
        return mClickToRefreshView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public int getCurrentPageCount() {
        return mCurrentPageCount;
    }

    public IRouter getRouter(){
        return mRouter;
    }
    //Getter end

    //before start
    protected void beforeInitializing() {

    }

    protected void beforeRefreshing() {

    }

    protected void beforeLoadingMore() {

    }
    //before end

    //after start
    protected void onDataFetched() {

    }

    //after end
    public class EventWhat {
        public static final int WHAT_UNINITIALIZED = 1;
        public static final int WHAT_NORMAL = 2;
        public static final int WHAT_EMPTY = 3;
        public static final int WHAT_EXCEPTION = 4;
    }
}
