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

package com.sxenon.pure.core.router;

import android.Manifest;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.permission.PermissionHelper;

import java.util.Arrays;
import java.util.List;

/**
 * RouterVisitor
 * Created by Sui on 2016/11/28.
 */

public abstract class PureRouterVisitorAsPresenter<R extends IRouter> extends BasePresenter<R> implements IRouterVisitor<R>, LifecycleOwner {

    private final PermissionHelper permissionHelper;
    private boolean isRequestingSystemAlertPermission;
    public static final String TAG = "PureRouterVisitorAsPresenter";
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    public PureRouterVisitorAsPresenter(R router) {
        super(router);
        permissionHelper = new PermissionHelper(router, this);
    }

    @Override
    public List<Event> getEventForSave() {
        return null;
    }

    //LifeCycle start
    @Override
    public void onCreate(List<Event> savedEventList) {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    @Override
    public void onStart() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    public void onResume() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onPause() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    @Override
    public void onStop() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    @Override
    public void onDestroy() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    //LifeCycle end
    @Override
    public boolean onBackPressed() {
        return false;
    }

    //Permission start

    public void setPermissionEvent(int what, Runnable runnable, boolean forceAccepting) {
        permissionHelper.setPermissionEvent(what, runnable, forceAccepting);
    }

    /**
     * Please ignore the return value unless the router type is COMPACT_ACTIVITY.
     *
     * @return Handle the result by self or deliver to its fragment,if the router type is COMPACT_ACTIVITY.
     */
    public final boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissionHelper.getPermissionEvent() == null) {
            throw new IllegalStateException("Please call requestPermissionsCompact in router(view) or requestPermissions in routerVisitor(presenter)");
        }
        if (getRouter().requestPermissionsBySelf(requestCode)) {
            permissionHelper.onRequestPermissionsResult(permissions, grantResults);
            return true;
        }
        return false;
    }

    /**
     * Please ignore the return value unless the router type is COMPACT_ACTIVITY.
     *
     * @return Handle the request by self or deliver to its fragment,if the router type is COMPACT_ACTIVITY.
     */
    public final boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isRequestingSystemAlertPermission) {
            permissionHelper.onRequestSystemAlertPermissionResult(resultCode);
            isRequestingSystemAlertPermission = false;
        } else if (getRouter().startActivityForResultBySelf(requestCode)) {
            handleActivityResult(requestCode, resultCode, data);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public final void requestPermissions(@NonNull String[] permissions, int requestCode, Runnable runnable, boolean forceAccepting) {
        if (Arrays.binarySearch(permissions, Manifest.permission.SYSTEM_ALERT_WINDOW) >= 0) {
            throw new IllegalArgumentException("Please Call requestSystemAlertPermission(int requestCode, Runnable runnable) for SYSTEM_ALERT_WINDOW!");
        }
        permissionHelper.requestPermissions(permissions, requestCode, runnable, forceAccepting);
    }

    @Override
    public final void requestSystemAlertPermission(int requestCode, Runnable runnable) {
        isRequestingSystemAlertPermission = !permissionHelper.showSystemAlertAtOnce(requestCode, runnable);
    }

    /**
     * @return if true,don`t forget to call {@link #requestPermissionsAfterExplanation} first!
     */
    @Override
    public boolean shouldExplainPermissionBeforeRequest(int requestCode, String[] permissions) {
        return false;
    }

    @Override
    public final void onPermissionGranted(Runnable runnable) {
        runnable.run();
    }

    @Override
    public void onPermissionDeclined(int requestCode, @NonNull String[] permissions) {

    }

    @Override
    public void onPermissionPermanentlyDeclined(int requestCode, @NonNull String[] permissions) {

    }

    @Override
    public final void requestPermissionsAfterExplanation(@NonNull String[] permissions) {
        permissionHelper.requestPermissionsAfterExplanation(permissions);
    }

    @NonNull
    public IRouter getRouter() {
        return getView();
    }
    //Permission end
}
