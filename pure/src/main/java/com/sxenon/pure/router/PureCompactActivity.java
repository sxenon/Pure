package com.sxenon.pure.router;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.hwangjr.rxbus.RxBus;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;
import com.sxenon.pure.global.GlobalContext;
import com.sxenon.pure.permission.PermissionHelper;

/**
 * 做最纯净的Activity二次封装
 * Created by Sui on 2016/11/21.
 */

public abstract class PureCompactActivity<P extends PureRootPresenter> extends AppCompatActivity implements IRouter<P> {
    private Event mSavedEvent;
    private P mRootPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseRootViewModule<P> rootViewModule = groupViewModule();
        mRootPresenter = rootViewModule.getPresenter();
        mRootPresenter.onCreate(mSavedEvent);
        RxBus.get().register(this);
        GlobalContext.INSTANCE.onActivityCreate(this);
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
        saveEvent(mRootPresenter.getEventForSave());
        mRootPresenter.onDestroy();
        RxBus.get().unregister(this);
        GlobalContext.INSTANCE.onActivityDestroy(this);
    }

    @Override
    public P getRootPresenter() {
        return mRootPresenter;
    }

    @Override
    public void saveEvent(Event event) {
        mSavedEvent = event;
    }

    @Override
    public Activity getActivityCompact() {
        return this;
    }

    @Override
    public void requestPermissionsCompact(@NonNull String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, PermissionHelper.REQUEST_PERMISSIONS);
    }

    @Override
    public RouterType getRouterType() {
        return RouterType.FRAGMENT_ACTIVITY;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mRootPresenter.onRequestPermissionsResult(permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRootPresenter.onActivityForResult(requestCode);
    }

    protected abstract BaseRootViewModule<P> groupViewModule();

}
