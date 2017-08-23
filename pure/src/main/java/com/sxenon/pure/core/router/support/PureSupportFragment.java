/*
 * Copyright (c) 2017 sxenon
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

package com.sxenon.pure.core.router.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public abstract class PureSupportFragment<P extends PureRouterVisitorAsPresenter> extends Fragment implements IFragment<P> {
    private List<Event> mSavedEventList;
    private P mRootPresenter;
    private boolean mViewCreated;
    private boolean shouldInitRootPresenter;
    /**
     * 真正的对用户可见的状态！！！
     */
    private boolean mVisible;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
        mRootPresenter = bindPresenter();
        mViewCreated = true;
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
        mViewCreated = false;
        shouldInitRootPresenter = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mVisible = mViewCreated && isVisibleToUser;
        initRootPresenterIfNeeded();
        PureCompactActivity activity = (PureCompactActivity) getActivity();
        if (mVisible) {
            activity.addToVisibleSet(this);
        } else {
            if (activity != null) {
                activity.removeFromVisibleSet(this);
            }
        }
    }

    private void initRootPresenterIfNeeded() {
        if (mVisible && shouldInitRootPresenter) {
            mRootPresenter.onCreate(mSavedEventList);
            shouldInitRootPresenter = false;
        }
    }

    @Override
    public P getPresenter() {
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
    public void requestPermissionsCompact(@NonNull String[] permissions, int requestCode, Action0 action, boolean forceAccepting) {
        getPresenter().setPermissionEvent(requestCode, action, forceAccepting);
        requestPermissions(permissions, requestCode);
    }

    @Override
    public final boolean requestPermissionsBySelf(int requestCode) {
        return true;
    }

    @Override
    public final RouterType getRouterType() {
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

    @Override
    public final boolean startActivityForResultBySelf(int requestCode) {
        return true;
    }

    public final boolean onBackPressed() {
        return mRootPresenter.onBackPressed();
    }

}
