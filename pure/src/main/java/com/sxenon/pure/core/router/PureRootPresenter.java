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

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.global.GlobalContext;
import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.permission.PermissionHelper;
import com.sxenon.pure.core.util.PureKeyboardUtil;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.OutsideLifecycleException;
import com.trello.rxlifecycle.RxLifecycle;

import java.util.List;

import javax.annotation.Nonnull;

import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Include several {@link BaseSubPresenter}，with its related {@link BaseSubViewModule}
 * Don`t use it directly,use PureRootPresenter instead.
 * Created by Sui on 2016/11/28.
 */

public abstract class PureRootPresenter<R extends IRouter> extends BasePresenter<R> implements IRouterVisitor<R> {

    private RouterEvent currentEvent;
    private final BehaviorSubject<RouterEvent> lifecycleSubject = BehaviorSubject.create();
    private final PermissionHelper permissionHelper;
    private boolean isRequestingSystemAlertPermission;
    public static final String TAG = "PureRootPresenter";
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

    public PureRootPresenter(R router) {
        super(router);
        permissionHelper = PermissionHelper.getInstance(getRouter(), this);
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
        if (requestCommonPermissionsBySelf(requestCode)) {
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
        } else if (startActivityForResultBySelf(requestCode)) {
            handleActivityResult(requestCode, resultCode, data);
        } else {
            return false;
        }
        return true;
    }

    /**
     * @return Return false if the router is instance of FragmentActivity and its supportFragment request the permission,otherwise true.
     */
    private boolean requestCommonPermissionsBySelf(int requestCode) {
        return getRouter().getRouterType() != IRouter.RouterType.COMPACT_ACTIVITY || shouldHandlePermissionsResultIfInCompactActivity(requestCode);
    }

    /**
     * @return Return false, if the router is instance of FragmentActivity and its supportFragment start activity,otherwise true.
     */
    private boolean startActivityForResultBySelf(int requestCode) {
        return getRouter().getRouterType() != IRouter.RouterType.COMPACT_ACTIVITY || shouldHandleActivityResultIfInCompactActivity(requestCode);
    }

    @Override
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public final void requestCommonPermissions(@NonNull String[] permissions, int requestCode, Action0 action) {
        permissionHelper.requestCommonPermissions(permissions, requestCode, action);
    }

    @Override
    public final void requestSystemAlertPermission(int requestCode, Action0 action) {
        isRequestingSystemAlertPermission = !permissionHelper.showSystemAlertAtOnce(requestCode, action);
    }

    @Override
    public boolean shouldPermissionExplainBeforeRequest(int requestCode, String[] permissions) {
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
    public void onPermissionReallyDeclined(int requestCode, @NonNull String[] permissions) {

    }

    @Override
    public final void requestAfterExplanation(@NonNull String[] permissions) {
        permissionHelper.requestAfterExplanation(permissions);
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

    public final void setOnKeyboardShowingListener(KeyboardUtil.OnKeyboardShowingListener listener) {
        PureKeyboardUtil.attach(this, listener);
    }

    /**
     * Only work when router is compactActivity
     */
    public boolean shouldHandleActivityResultIfInCompactActivity(int requestCode) {
        return false;
    }

    /**
     * Only work when router is compactActivity
     */
    public boolean shouldHandlePermissionsResultIfInCompactActivity(int requestCode) {
        return false;
    }
    //Binding end
}
