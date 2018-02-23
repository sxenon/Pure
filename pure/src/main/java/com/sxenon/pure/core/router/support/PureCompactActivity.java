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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.global.GlobalContext;
import com.sxenon.pure.core.router.IActivity;
import com.sxenon.pure.core.router.PureRouterVisitorAsPresenter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * To be the purest wrapper for Activity
 * Attention:Those activities which call finish in onCreate() should not inherit it.
 * Created by Sui on 2016/11/21.
 */

public abstract class PureCompactActivity<P extends PureRouterVisitorAsPresenter> extends AppCompatActivity implements IActivity<P> {
    private P mPresenter;
    private final Set<PureSupportFragment> mVisibleFragmentSet = new HashSet<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        mPresenter = bindPresenter();
        mPresenter.onCreate(savedInstanceState == null ? null : GlobalContext.INSTANCE.savedEventList);
        GlobalContext.INSTANCE.activityHistoryManager.add(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mPresenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //noinspection unchecked
        saveEventList(mPresenter.getEventForSave());
        mPresenter.onDestroy();
        GlobalContext.INSTANCE.activityHistoryManager.remove(this);
    }

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void saveEventList(List<Event> eventList) {
        GlobalContext.INSTANCE.savedEventList = eventList;
    }

    @Override
    public Activity getActivityCompact() {
        return this;
    }

    @Override
    public void requestPermissionsCompact(@NonNull String[] permissions, int requestCode, Runnable runnable, boolean forceAccepting) {
        getPresenter().setPermissionEvent(requestCode, runnable, forceAccepting);
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public final RouterType getRouterType() {
        return RouterType.COMPACT_ACTIVITY;
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            //deliver to v4.fragment
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mPresenter.onActivityResult(requestCode, resultCode, data)) {
            //deliver to v4.fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void addToVisibleSet(PureSupportFragment pureSupportFragment) {
        mVisibleFragmentSet.add(pureSupportFragment);
    }

    void removeFromVisibleSet(PureSupportFragment pureSupportFragment) {
        mVisibleFragmentSet.remove(pureSupportFragment);
    }

    @Override
    public final void onBackPressed() {
        boolean handled = false;
        for (PureSupportFragment visibleFragment : mVisibleFragmentSet) {
            if (visibleFragment.onBackPressed()) {
                handled = true;
            }
        }
        if (!handled && !mPresenter.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean shouldKeepWhenInBackground(int finishForWhat) {
        return true;
    }

}
