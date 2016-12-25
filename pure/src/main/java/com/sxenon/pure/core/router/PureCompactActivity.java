package com.sxenon.pure.core.router;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.hwangjr.rxbus.RxBus;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;
import com.sxenon.pure.core.global.GlobalContext;
import com.sxenon.pure.core.permission.PermissionHelper;

import java.util.List;

/**
 * 做最纯净的Activity二次封装
 * Created by Sui on 2016/11/21.
 */

public abstract class PureCompactActivity<P extends PureRootPresenter> extends AppCompatActivity implements IRouter<P> {
    private P mRootPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseRootViewModule<P> rootViewModule = groupViewModule();
        mRootPresenter = rootViewModule.getPresenter();
        mRootPresenter.onCreate(savedInstanceState==null?null:GlobalContext.INSTANCE.savedEventList);
        //To replace intent with data
        RxBus.get().register(this);
        GlobalContext.INSTANCE.activityHistoryManager.add(this);
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
        //noinspection unchecked
        saveEventList(mRootPresenter.getEventForSave());
        mRootPresenter.onDestroy();
        RxBus.get().unregister(this);
        GlobalContext.INSTANCE.activityHistoryManager.remove(this);
    }

    @Override
    public P getRootPresenter() {
        return mRootPresenter;
    }

    @Override
    public void saveEventList(List<Event> eventList) {
        GlobalContext.INSTANCE.savedEventList=eventList;
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRootPresenter.onActivityForResult(requestCode);
    }

    protected abstract BaseRootViewModule<P> groupViewModule();

}
