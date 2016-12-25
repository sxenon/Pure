package com.sxenon.pure.core.router;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.binder.IViewBinder;
import com.sxenon.pure.core.binder.ViewBinderImpl;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;
import com.sxenon.pure.core.permission.OnPermissionCallback;
import com.sxenon.pure.core.permission.PermissionHelper;
import com.sxenon.pure.core.router.app.PureActivity;
import com.sxenon.pure.core.router.app.PureFragment;
import com.sxenon.pure.core.router.support.PureCompactActivity;
import com.sxenon.pure.core.router.support.PureCompactRootPresenter;
import com.sxenon.pure.core.router.support.PureSupportFragment;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.OutsideLifecycleException;
import com.trello.rxlifecycle.RxLifecycle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Don`t use it directly!
 * Use {@link PureCompactRootPresenter} for {@link PureCompactActivity}
 * Use {@link PureRootPresenter} for {@link PureActivity} or {@link PureFragment} or {@link PureSupportFragment}
 * Created by Sui on 2016/11/28.
 */

//TODO RxLifeCycle 学习
public abstract class PureBaseRootPresenter<VM extends BaseRootViewModule> extends BaseRootPresenter<VM> implements LifecycleProvider<RouterEvent>, OnPermissionCallback {

    private final BehaviorSubject<RouterEvent> lifecycleSubject = BehaviorSubject.create();
    private final PermissionHelper permissionHelper;
    private final List<Subscription> subscriptions = new ArrayList<>();
    private IViewBinder mViewBinder;
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
                            throw new OutsideLifecycleException("Cannot bind to Router lifecycle when outside of it.");
                        default:
                            throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
                    }
                }
            };

    public PureBaseRootPresenter(VM viewModule) {
        super(viewModule);
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
        super.onCreate(savedEventList);
        lifecycleSubject.onNext(RouterEvent.CREATE);
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
        unSubscribeSubscription();
    }
    //LifeCycle end

    //Permission start
    public boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCommonPermissionsSelf(requestCode)) {
            permissionHelper.onRequestPermissionsResult(permissions, grantResults);
            return true;
        }
        return false;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (startActivitySelf(requestCode)) {
            if (requestSystemAlertPermissionSelf(requestCode)) {
                permissionHelper.onRequestSystemAlertPermissionResult(resultCode);
            } else {
                handleActivityResult(requestCode, resultCode, data);
            }
            return true;
        }
        return false;
    }

    /**
     * @return false, if the router is instance of FragmentActivity and its supportFragment request the permission,otherwise true.
     */
    protected abstract boolean requestCommonPermissionsSelf(int requestCode);

    /**
     * @return false, if the router is instance of FragmentActivity and its supportFragment request the permission,otherwise true.
     */
    protected abstract boolean requestSystemAlertPermissionSelf(int requestCode);

    /**
     * @return false, if the router is instance of FragmentActivity and its supportFragment start activity,otherwise true.
     */
    protected abstract boolean startActivitySelf(int requestCode);

    protected void handleActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void requestCommonPermissions(int requestCode, @NonNull String[] permissions, Action0 action) {
        permissionHelper.requestCommonPermissions(permissions, requestCode, action);
    }

    public void requestSystemAlertPermission(int requestCode, Action0 action) {
        permissionHelper.requestSystemAlertPermission(requestCode, action);
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

    //Binding start
    public IViewBinder getViewBinder() {
        if (mViewBinder == null) {
            final IViewBinder viewBinder = new ViewBinderImpl();
            mViewBinder = (IViewBinder) Proxy.newProxyInstance(viewBinder.getClass().getClassLoader(), viewBinder.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Subscription subscription = (Subscription) method.invoke(viewBinder, args);
                    subscriptions.add(subscription);
                    return subscription;
                }
            });
        }
        return mViewBinder;
    }

    private void unSubscribeSubscription() {
        for (Subscription subscription : subscriptions) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }
    //Binding end
}
