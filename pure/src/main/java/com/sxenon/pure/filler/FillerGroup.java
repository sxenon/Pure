package com.sxenon.pure.filler;

import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.IPureAdapter;
import com.sxenon.pure.filler.pull.BasePullLayout;
import com.sxenon.pure.filler.pull.IPullLayout;
import com.sxenon.pure.util.Preconditions;

import java.util.List;

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
    private final boolean mRefreshForAdd;

    private View mEmptyView;
    private View mExceptionView;
    private View mClickToRefreshView;

    public FillerGroup(Context context, PL pullLayout, IFetchSingleResultHandler<R> singleDataResult) {
        this(context, pullLayout, null, singleDataResult, false);
    }

    public FillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter) {
        this(context, pullLayout, adapter, null, false);
    }

    public FillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, boolean freshForAdd) {
        this(context, pullLayout, adapter, null, freshForAdd);
    }

    private FillerGroup(Context context, PL pullLayout, IPureAdapter<R> adapter, IFetchSingleResultHandler<R> singleDataResult, boolean freshForAdd) {
        mContext = context;
        mPullLayout = pullLayout;
        mAdapter = adapter;
        mSingleDataResult = singleDataResult;
        mRefreshForAdd = freshForAdd;
    }

    public void setRefreshDelegate(final IPullLayout.RefreshDelegate delegate){
        mPullLayout.setDelegate(new IPullLayout.RefreshDelegate() {
            @Override
            public void onBeginRefreshing() {
                //TODO
                delegate.onBeginRefreshing();
            }

            @Override
            public boolean onBeginLoadingMore() {
                //TODO
                return delegate.onBeginLoadingMore();
            }
        });
    }

    public void setMinorComponents(View emptyView,View exceptionView,View clickToRefreshView){
        mEmptyView=emptyView;
        mExceptionView=exceptionView;
        mClickToRefreshView=clickToRefreshView;

        resetMinorComponents();
        if (mClickToRefreshView!=null){
            //TODO RxBinding subscribe and unSubscribe
            mClickToRefreshView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    beginRefreshing();
                }
            });
        }
    }

    private void resetMinorComponents(){
        if (mClickToRefreshView!=null){
            mClickToRefreshView.setVisibility(View.GONE);
        }
        if (mEmptyView!=null){
            mEmptyView.setVisibility(View.GONE);
        }
        if (mExceptionView!=null){
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

    @Override
    public void onSingleDataFetched(R data) {
        mValue = data;
        eventWhat = EventWhat.WHAT_NORMAL;
        mSingleDataResult.onSingleDataFetched(data);
    }

    @Override
    public void onEmpty() {
        eventWhat = EventWhat.WHAT_EMPTY;
        if (mEmptyView!=null){
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onListDataFetched(List<R> data) {
        eventWhat = EventWhat.WHAT_NORMAL;
        resetMinorComponents();
    }

    @Override
    public void onCancel() {
        mCurrentPageCount=tempPageCount;
    }

    @Override
    public void onException(ApiException exception) {
        eventWhat = EventWhat.WHAT_EXCEPTION;
        mException = exception;
        resetMinorComponents();
        if (mExceptionView!=null){
            mExceptionView.setVisibility(View.VISIBLE);
            if (mClickToRefreshView!=null){
                mClickToRefreshView.setVisibility(View.VISIBLE);
            }
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
    //Getter end

    public class EventWhat {
        public static final int WHAT_UNINITIALIZED = 1;
        public static final int WHAT_NORMAL = 2;
        public static final int WHAT_EMPTY = 3;
        public static final int WHAT_EXCEPTION = 4;
    }
}
