package com.sxenon.pure.core.router;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.root.RootPresenterEvent;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;
import com.sxenon.pure.core.permission.OnPermissionCallback;
import com.sxenon.pure.core.permission.PermissionHelper;
import com.sxenon.pure.core.util.PureKeyboardUtil;
import com.trello.rxlifecycle.LifecycleProvider;
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
 * Created by Sui on 2016/11/28.
 */

public abstract class PureRootPresenter<VM extends BaseRootViewModule> extends BaseRootPresenter<VM> implements LifecycleProvider<RootPresenterEvent>, OnPermissionCallback {

    private final BehaviorSubject<RootPresenterEvent> lifecycleSubject = BehaviorSubject.create();
    private final PermissionHelper permissionHelper;
    private boolean isRequestingSystemAlertPermission;
    private final Func1<RootPresenterEvent, RootPresenterEvent> ROUTER_LIFECYCLE =
            new Func1<RootPresenterEvent, RootPresenterEvent>() {
                @Override
                public RootPresenterEvent call(RootPresenterEvent lastEvent) {
                    switch (lastEvent) {
                        case CREATE:
                            return RootPresenterEvent.DESTROY;
                        case RESUME:
                            return RootPresenterEvent.PAUSE;
                        case PAUSE:
                            return RootPresenterEvent.STOP;
                        case STOP:
                            return RootPresenterEvent.DESTROY;
                        case DESTROY:
                            throw new OutsideLifecycleException("Cannot bind to Router lifecycle when outside of it.");
                        default:
                            throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
                    }
                }
            };

    public PureRootPresenter(VM viewModule) {
        super(viewModule);
        permissionHelper = PermissionHelper.getInstance(getRouter(), this);
    }

    //LifecycleProvider start
    @Nonnull
    @Override
    public Observable<RootPresenterEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@Nonnull RootPresenterEvent event) {
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
        super.onCreate(savedEventList);
        lifecycleSubject.onNext(RootPresenterEvent.CREATE);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(RootPresenterEvent.RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        lifecycleSubject.onNext(RootPresenterEvent.PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycleSubject.onNext(RootPresenterEvent.STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(RootPresenterEvent.DESTROY);
    }
    //LifeCycle end

    public boolean onBackPressed() {
        return false;
    }

    //Permission start
    public boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCommonPermissionsBySelf(requestCode)) {
            permissionHelper.onRequestPermissionsResult(permissions, grantResults);
            return true;
        }
        return false;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
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
        return getRouter().getRouterType() != IRouter.RouterType.COMPACT_ACTIVITY || shouldHandlePermissionsResultInCompactActivity(requestCode);
    }

    /**
     * @return Return false, if the router is instance of FragmentActivity and its supportFragment start activity,otherwise true.
     */
    private boolean startActivityForResultBySelf(int requestCode) {
        return getRouter().getRouterType() != IRouter.RouterType.COMPACT_ACTIVITY || shouldHandleActivityResultIfInCompactActivity(requestCode);
    }

    protected void handleActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void requestCommonPermissions(@NonNull String[] permissions, int requestCode, Action0 action) {
        permissionHelper.requestCommonPermissions(permissions, requestCode, action);
    }

    public void requestSystemAlertPermission(int requestCode, Action0 action) {
        isRequestingSystemAlertPermission = !permissionHelper.showSystemAlertAtOnce(requestCode, action);
    }

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

    public void requestAfterExplanation(@NonNull String[] permissions) {
        permissionHelper.requestAfterExplanation(permissions);
    }
    //Permission end

    @Override
    public <R> Observable<R> autoUnsubscribe(Observable<R> observable){
        //noinspection unchecked
        return (Observable<R>) observable.compose(bindUntilEvent(RootPresenterEvent.DESTROY));
    }

    public void registerActionOnResume(Action0 action){
        registerActionOnEvent(RootPresenterEvent.RESUME,action);
    }

    public void registerActionOnPause(Action0 action){
        registerActionOnEvent(RootPresenterEvent.PAUSE,action);
    }

    public void registerActionOnStop(Action0 action){
        registerActionOnEvent(RootPresenterEvent.STOP,action);
    }

    public void registerActionOnDestroy(Action0 action){
        registerActionOnEvent(RootPresenterEvent.DESTROY,action);
    }


    private void registerActionOnEvent(RootPresenterEvent rootPresenterEvent, Action0 action){
        Observable.never()
                /**
                 * RxLifecycle does not actually unsubscribe the sequence. Instead it terminates the sequence. The way in which it does so varies based on the type:
                 Observable - emits onCompleted()
                 Single and Completable - emits onError(CancellationException)
                 */
                .doOnTerminate(action)
                .compose(bindUntilEvent(rootPresenterEvent))
                .subscribe();
    }

    @Override
    public void setOnKeyboardShowingListener(KeyboardUtil.OnKeyboardShowingListener listener) {
        PureKeyboardUtil.attach(this, listener);
    }

    /**
     * Only work when router is compactActivity
     */
    protected boolean shouldHandleActivityResultIfInCompactActivity(int requestCode) {
        return false;
    }

    /**
     * Only work when router is compactActivity
     */
    protected boolean shouldHandlePermissionsResultInCompactActivity(int requestCode) {
        return false;
    }
    //Binding end
}
