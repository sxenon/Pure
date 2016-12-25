package com.sxenon.pure.core.router.support;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.hwangjr.rxbus.RxBus;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.router.PureRootPresenter;

import java.util.List;

/**
 * 做最纯净的Fragment二次封装
 * Created by Sui on 2016/11/21.
 */

public abstract class PureSupportFragment<P extends PureRootPresenter> extends Fragment implements IRouter<P> {
    private List<Event> mSavedEventList;
    private P mRootPresenter;
    private boolean mCreated;
    private boolean shouldInitRootPresenter;
    /**
     * 真正的对用户可见的状态！！！
     */
    private boolean mVisible;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseRootViewModule<P> rootViewModule = groupViewModule(view);
        mRootPresenter = rootViewModule.getPresenter();
        //To replace "setArguments"
        RxBus.get().register(this);
        mCreated = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRootPresenterIfNeeded();
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
        shouldInitRootPresenter = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mVisible = mCreated && isVisibleToUser;
        initRootPresenterIfNeeded();
        PureCompactActivity activity = (PureCompactActivity) getActivity();
        if (mVisible) {
            activity.addToVisibleSet(this);
        } else {
            activity.removeFromVisibleSet(this);
        }
    }

    private void initRootPresenterIfNeeded() {
        if (mVisible && shouldInitRootPresenter) {
            mRootPresenter.onCreate(mSavedEventList);
            shouldInitRootPresenter = false;
        }
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
    public void requestPermissionsCompact(@NonNull String[] permissions, int requestCode) {
        requestPermissions(permissions, requestCode);
    }

    @Override
    public RouterType getRouterType() {
        return RouterType.SUPPORT_FRAGMENT;
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mRootPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        mRootPresenter.onActivityResult(requestCode, resultCode, data);
    }

    boolean onBackPressed() {
        return mRootPresenter.onBackPressed();
    }

    @Override
    public void clearFragmentBackStackImmediate() {
        FragmentManager manager = getChildFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStackImmediate(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    protected abstract BaseRootViewModule<P> groupViewModule(View view);

}
