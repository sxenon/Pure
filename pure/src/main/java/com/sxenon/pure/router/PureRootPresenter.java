package com.sxenon.pure.router;

import android.support.annotation.NonNull;

import com.hwangjr.rxbus.RxBus;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;
import com.sxenon.pure.permission.OnPermissionCallback;
import com.sxenon.pure.permission.OnRequestPermissionsResult;
import com.sxenon.pure.permission.PermissionHelper;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.OutsideLifecycleException;
import com.trello.rxlifecycle.RxLifecycle;

import javax.annotation.Nonnull;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * TODO RxLifeCycle 学习
 * Created by Sui on 2016/11/28.
 */

public abstract class PureRootPresenter<VM extends BaseRootViewModule> extends BaseRootPresenter<VM> implements LifecycleProvider<RouterEvent>,OnRequestPermissionsResult,OnPermissionCallback {

    private final BehaviorSubject<RouterEvent> lifecycleSubject = BehaviorSubject.create();
    private final PermissionHelper permissionHelper;


    private static final Func1<RouterEvent, RouterEvent> ROUTER_LIFECYCLE =
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
                            throw new OutsideLifecycleException("Cannot bind to Router lifecycle when outside of it.");
                        default:
                            throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
                    }
                }
            };

    public PureRootPresenter(VM viewModule) {
        super(viewModule);
        permissionHelper=PermissionHelper.getInstance(getRouter(),this);
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
    public void onCreate(Event savedEvent) {
        super.onCreate(savedEvent);
        lifecycleSubject.onNext(RouterEvent.CREATE);
        RxBus.get().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(RouterEvent.RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        lifecycleSubject.onNext(RouterEvent.PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycleSubject.onNext(RouterEvent.STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(RouterEvent.DESTROY);
        RxBus.get().unregister(this);
    }

    //LifeCycle end

    //Permission start
    @Override
    public void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(permissions, grantResults);
    }


    @Override
    public void onActivityForResult(int requestCode) {
        permissionHelper.onActivityForResult(requestCode);
    }

    @Override
    public void requestCommonPermissions(@NonNull String[] permissions, int what, Action0 action) {
        permissionHelper.requestCommonPermissions(permissions,what,action);

    }

    public void requestSystemAlertPermission(int what, Action0 action){
        permissionHelper.requestSystemAlertPermission(what,action);
    }


    public boolean shouldPermissionExplainBeforeRequest(String[] permissions, int what){
        return false;
    }

    @Override
    public final void onPermissionGranted(@NonNull String[] permissions, Action0 action) {
        action.call();
    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissions, int permissionWhat) {

    }

    @Override
    public void onPermissionReallyDeclined(@NonNull String[] permissions,int permissionWhat) {

    }

    public void requestAfterExplanation(@NonNull String[] permissions){
        permissionHelper.requestAfterExplanation(permissions);
    }
    //Permission end
}
