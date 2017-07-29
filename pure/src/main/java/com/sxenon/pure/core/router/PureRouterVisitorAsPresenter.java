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
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.global.GlobalContext;
import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.permission.PermissionHelper;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.OutsideLifecycleException;
import com.trello.rxlifecycle.RxLifecycle;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Include several {@link BaseSubPresenter}s，with their related {@link BaseSubView}s
 * Created by Sui on 2016/11/28.
 */

public abstract class PureRouterVisitorAsPresenter<R extends IRouter> extends BasePresenter<R> implements IRouterVisitor<R> {

    private RouterEvent currentEvent;
    private final BehaviorSubject<RouterEvent> lifecycleSubject = BehaviorSubject.create();
    private final PermissionHelper permissionHelper;
    private boolean isRequestingSystemAlertPermission;
    public static final String TAG = "PureRouterVisitorAsPresenter";
    private final Func1<RouterEvent, RouterEvent> ROUTER_LIFECYCLE =
            new Func1<RouterEvent, RouterEvent>() {
                @Override
                public RouterEvent call(RouterEvent lastEvent) {
                    switch (lastEvent) {
                        case CREATE:
                            return RouterEvent.DESTROY;
                        case RESUME:
                            return RouterEvent.PAUSE;
                        case PAUSE:
                            return RouterEvent.STOP;
                        case STOP:
                            return RouterEvent.DESTROY;
                        case DESTROY:
                            throw new OutsideLifecycleException("Cannot bind to RootPresenter lifecycle when outside of it.");
                        default:
                            throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
                    }
                }
            };

    public PureRouterVisitorAsPresenter(R router) {
        super(router);
        permissionHelper = new PermissionHelper(router, this);
    }

    //LifecycleProvider start
    @Nonnull
    @Override
    public Observable<RouterEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@Nonnull RouterEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        //  return RxLifecycle.
        return RxLifecycle.bind(lifecycleSubject, ROUTER_LIFECYCLE);
    }
    //LifecycleProvider end

    @Override
    public List<Event> getEventForSave() {
        return null;
    }

    //LifeCycle start
    @Override
    public void onCreate(List<Event> savedEventList) {
        currentEvent = RouterEvent.CREATE;
        GlobalContext.INSTANCE.routerLifecycleCallbackDispatcher.dispatchRouterCreated(this, savedEventList);
        lifecycleSubject.onNext(RouterEvent.CREATE);
    }

    @Override
    public void onResume() {
        currentEvent = RouterEvent.RESUME;
        GlobalContext.INSTANCE.routerLifecycleCallbackDispatcher.dispatchRouterResumed(this);
        lifecycleSubject.onNext(RouterEvent.RESUME);
    }

    @Override
    public void onPause() {
        currentEvent = RouterEvent.PAUSE;
        GlobalContext.INSTANCE.routerLifecycleCallbackDispatcher.dispatchRouterPaused(this);
        lifecycleSubject.onNext(RouterEvent.PAUSE);
    }

    @Override
    public void onStop() {
        currentEvent = RouterEvent.STOP;
        GlobalContext.INSTANCE.routerLifecycleCallbackDispatcher.dispatchRouterStopped(this);
        lifecycleSubject.onNext(RouterEvent.STOP);
    }

    @Override
    public void onDestroy() {
        currentEvent = RouterEvent.DESTROY;
        GlobalContext.INSTANCE.routerLifecycleCallbackDispatcher.dispatchRouterDestroyed(this);
        lifecycleSubject.onNext(RouterEvent.DESTROY);
    }

    @Override
    public RouterEvent getCurrentEvent() {
        return currentEvent;
    }

    //LifeCycle end
    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }
    //Permission start

    /**
     * Please ignore the return value unless the router type is COMPACT_ACTIVITY.
     *
     * @return Handle the result by self or deliver to its fragment,if the router type is COMPACT_ACTIVITY.
     */
    public final boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
    public final void requestCommonPermissions(@NonNull String[] permissions, int requestCode, Action0 action,boolean forceAccepting) {
        if (Arrays.binarySearch(permissions, Manifest.permission.SYSTEM_ALERT_WINDOW) >= 0) {
            throw new IllegalArgumentException("Please Call requestSystemAlertPermission(int requestCode, Action0 action) for SYSTEM_ALERT_WINDOW!");
        }
        permissionHelper.requestCommonPermissions(permissions, requestCode, action,forceAccepting);
    }

    @Override
    public final void requestSystemAlertPermission(int requestCode, Action0 action) {
        isRequestingSystemAlertPermission = !permissionHelper.showSystemAlertAtOnce(requestCode, action);
    }

    /**
     *
     * @return if true,don`t forget to call {@link #requestPermissionsAfterExplanation} first!
     */
    @Override
    public boolean shouldExplainPermissionBeforeRequest(int requestCode, String[] permissions) {
        return false;
    }

    @Override
    public final void onPermissionGranted(Action0 action) {
        action.call();
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
    //Permission end

    /**
     * RxLifecycle does not actually unsubscribe the sequence. Instead it terminates the sequence. The way in which it does so varies based on the type:
     * Observable - emits onCompleted()
     * Single and Completable - emits onError(CancellationException)
     */
    @NonNull
    @Override
    public final <T> LifecycleTransformer<T> autoComplete() {
        return bindUntilEvent(RouterEvent.DESTROY);
    }
    //Binding end
}
