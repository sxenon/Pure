package com.sxenon.pure.router;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.IRouter;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;

/**
 * TODO RxAppCompatActivity研究
 * Created by Sui on 2016/11/21.
 */

public abstract class PureActivity<P extends PureRootPresenter> extends AppCompatActivity implements IRouter<P> {
    private Event mSavedEvent;
    private P mRootPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //noinspection unchecked
        mRootPresenter= (P) new BaseRootPresenter<>(groupViewModule());
        mRootPresenter.onCreate(mSavedEvent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRootPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRootPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRootPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRootPresenter.onDestroy();
    }

    @Override
    public P getRootPresenter() {
        return mRootPresenter;
    }

    @Override
    public void saveEvent(Event event) {
        mSavedEvent=event;
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }
}
