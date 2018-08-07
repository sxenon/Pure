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

package com.sxenon.pure.core.controller.impl;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.sxenon.pure.core.controller.IActivity;

/**
 * To be the purest wrapper for Activity
 * Attention:Those activities which call finish in onCreate() should not inherit it.
 * Created by Sui on 2016/11/21.
 */

public abstract class AbstractCompactActivity<P extends AbstractControllerVisitorAsPresenter> extends AppCompatActivity implements IActivity<P> {
    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        mPresenter = bindPresenter();
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
    public Activity getActivityCompact() {
        return this;
    }

    @Override
    public void requestPermissionsCompact(@NonNull String[] permissions, int requestCode, Runnable runnable, boolean forceAccepting) {
        getPresenter().setPermissionEvent(requestCode, runnable, forceAccepting);
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public final ControllerType getControllerType() {
        return ControllerType.COMPACT_ACTIVITY;
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

    @Override
    public boolean shouldKeepWhenInBackground(int finishForWhat) {
        return true;
    }

    @Override
    public ViewModelProvider getViewModelProvider() {
        return ViewModelProviders.of(this);
    }

    @Override
    public ViewModelProvider getViewModelProvider(ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(this,factory);
    }
}
