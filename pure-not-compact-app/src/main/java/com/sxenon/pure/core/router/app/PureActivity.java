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
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.global.GlobalContext;
import com.sxenon.pure.core.router.IActivity;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.router.PureRootPresenter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * To be the purest wrapper for Activity
 * Attention:Those activities which call finish in onCreate() should not inherit it.
 * Created by Sui on 2016/11/21.
 */

public abstract class PureActivity<P extends PureRootPresenter> extends Activity implements IActivity<P> {
    private P mRootPresenter;
    private final Set<PureFragment> mVisibleFragmentSet=new HashSet<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootPresenter= bindVisitor();
        mRootPresenter.onCreate(savedInstanceState==null?null:GlobalContext.INSTANCE.savedEventList);
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
        GlobalContext.INSTANCE.activityHistoryManager.remove(this);
    }

    @Override
    public P getVisitor() {
        return mRootPresenter;
    }

    @NonNull
    @Override
    public IRouter getRouter() {
        return this;
    }

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public P getPresenter() {
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
    public void requestPermissionsCompact(@NonNull String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public final RouterType getRouterType() {
        return RouterType.ACTIVITY;
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mRootPresenter.onRequestPermissionsResult(requestCode,permissions, grantResults);
    }

    @Override
    protected final void onActivityResult(int requestCode, int resultCode, Intent data) {
        mRootPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public final void onBackPressed() {
        boolean handled=false;
        for (PureFragment visibleFragment:mVisibleFragmentSet){
            if (visibleFragment.onBackPressed()){
                handled=true;
            }
        }
        if (!handled&&!mRootPresenter.onBackPressed()){
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mRootPresenter.onConfigurationChanged(newConfig);
    }

    void addToVisibleSet(PureFragment pureFragment){
        mVisibleFragmentSet.add(pureFragment);
    }

    void removeFromVisibleSet(PureFragment pureFragment){
        mVisibleFragmentSet.remove(pureFragment);
    }

    public void clearFragmentBackStackImmediate(){
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount()>0){
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStackImmediate(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public boolean shouldKeepWhenBackground(int what) {
        return true;
    }

}
