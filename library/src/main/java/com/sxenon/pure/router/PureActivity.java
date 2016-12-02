package com.sxenon.pure.router;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.IRouter;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;

/**
 * 做最纯净的Activity二次封装
 * Created by Sui on 2016/11/21.
 */

public abstract class PureActivity<P extends PureRootPresenter> extends AppCompatActivity implements IRouter<P> {
    private Event mSavedEvent;
    private P mRootPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseRootViewModule<P> rootViewModule=groupViewModule();
        mRootPresenter=rootViewModule.getPresenter();
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
    public Activity getActivityCompact() {
        return this;
    }

    @Override
    public void requestPermissionsCompact(@NonNull String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mRootPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected abstract BaseRootViewModule<P> groupViewModule();

}
