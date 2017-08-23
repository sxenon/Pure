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

package com.sxenon.pure.core.router.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.view.View;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.router.IFragment;
import com.sxenon.pure.core.router.PureRouterVisitorAsPresenter;

import java.util.List;

import rx.functions.Action0;

/**
 * 做最纯净的Fragment二次封装
 * Created by Sui on 2016/11/21.
 */

public abstract class PureFragment<P extends PureRouterVisitorAsPresenter> extends Fragment implements IFragment<P> {
    private List<Event> mSavedEventList;
    private P mPresenter;
    private boolean mViewCreated;
    private boolean hasPresenterInitialized;
    /**
     * 真正的对用户可见的状态！！！
     */
    private boolean mVisible;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
        mPresenter = bindPresenter();
        mViewCreated = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPresenterIfNeeded();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //noinspection unchecked
        saveEventList(mPresenter.getEventForSave());
        mPresenter.onDestroy();
        mViewCreated = false;
        hasPresenterInitialized = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mVisible = mViewCreated && isVisibleToUser;
        initPresenterIfNeeded();
        PureActivity activity = (PureActivity) getActivity();
        if (mVisible) {
            activity.addToVisibleSet(this);
        } else {
            if (activity != null) {
                activity.removeFromVisibleSet(this);
            }
        }
    }

    private void initPresenterIfNeeded() {
        if (mVisible && !hasPresenterInitialized) {
            mPresenter.onCreate(mSavedEventList);
            hasPresenterInitialized = true;
        }
    }

    @Override
    public P getPresenter() {
        return mPresenter;
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
    public Context getContext() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return super.getContext();
        }else {
            return getActivity();
        }
    }

    @Override
    public void requestPermissionsCompact(@NonNull String[] permissions, int requestCode, Action0 action, boolean forceAccepting) {
        getPresenter().setPermissionEvent(requestCode, action, forceAccepting);
        FragmentCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public final RouterType getRouterType() {
        return RouterType.FRAGMENT;
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public final boolean requestPermissionsBySelf(int requestCode) {
        return true;
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public final boolean startActivityForResultBySelf(int requestCode) {
        return true;
    }

    public final boolean onBackPressed() {
        return mPresenter.onBackPressed();
    }

}
