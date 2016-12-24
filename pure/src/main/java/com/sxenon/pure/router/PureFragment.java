package com.sxenon.pure.router;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.view.View;

import com.hwangjr.rxbus.RxBus;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;
import com.sxenon.pure.permission.PermissionHelper;

import java.util.List;

/**
 * 做最纯净的Fragment二次封装
 * Created by Sui on 2016/11/21.
 */

public abstract class PureFragment<P extends PureRootPresenter> extends Fragment implements IRouter<P> {
    private List<Event> mSavedEventList;
    private P mRootPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseRootViewModule<P> rootViewModule = groupViewModule(view);
        mRootPresenter = rootViewModule.getPresenter();
        mRootPresenter.onCreate(mSavedEventList);
        //To replace "setArguments"
        RxBus.get().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRootPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRootPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mRootPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //noinspection unchecked
        saveEventList(mRootPresenter.getEventForSave());
        mRootPresenter.onDestroy();
        RxBus.get().unregister(this);
    }

    @Override
    public P getRootPresenter() {
        return mRootPresenter;
    }

    @Override
    public void saveEventList(List<Event> eventList) {
        mSavedEventList = eventList;
    }

    @Override
    public Activity getActivityCompact() {
        return getActivity();
    }

    @Override
    public void requestPermissionsCompact(@NonNull String[] permissions) {
        FragmentCompat.requestPermissions(this, permissions, PermissionHelper.REQUEST_PERMISSIONS);
    }

    @Override
    public RouterType getRouterType() {
        return RouterType.FRAGMENT;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mRootPresenter.onRequestPermissionsResult(permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRootPresenter.onActivityForResult(requestCode);
    }

    protected abstract BaseRootViewModule<P> groupViewModule(View view);

}
