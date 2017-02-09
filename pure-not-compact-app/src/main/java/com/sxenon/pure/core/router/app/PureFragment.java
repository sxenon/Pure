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
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.view.View;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.router.IFragment;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.router.PureRouterPresenter;

import java.util.List;

/**
 * 做最纯净的Fragment二次封装
 * Created by Sui on 2016/11/21.
 */

public abstract class PureFragment<P extends PureRouterPresenter> extends Fragment implements IFragment<P> {
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
        PureActivity activity = (PureActivity) getActivity();
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

    @NonNull
    @Override
    public IRouter getRouter() {
        return this;
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
    public void requestPermissionsCompact(@NonNull String[] permissions, int requestCode) {
        FragmentCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public final RouterType getRouterType() {
        return RouterType.FRAGMENT;
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mRootPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        mRootPresenter.onActivityResult(requestCode, resultCode, data);
    }

    public final boolean onBackPressed() {
        return mRootPresenter.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mRootPresenter.onConfigurationChanged(newConfig);
    }

    @Override
    public void clearFragmentBackStackImmediate() {
        FragmentManager manager = getChildFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStackImmediate(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

}
