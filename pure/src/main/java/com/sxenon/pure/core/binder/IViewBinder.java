package com.sxenon.pure.core.binder;

import android.view.View;

import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;

import rx.Subscription;
import rx.functions.Action1;

/**
 * {@link ViewBinderImpl}
 * Dynamic Proxy
 * {@link BaseRootPresenter#getViewBinder()}} or {@link BasePresenter#getViewBinderForBinding()}
 * Created by Sui on 2016/10/26.
 */

@SuppressWarnings("UnusedReturnValue")
public interface IViewBinder {
    /**
     * 在默认的间隔内只执行第一次
     *
     * @param view   被点击的view
     * @param onNext action
     */
    Subscription bindViewClickButEmitOnlyFirst(View view, final Action1<? super Void> onNext);

    /**
     * 在指定的间隔内只执行第一次
     *
     * @param view     被点击的view
     * @param onNext   action
     * @param duration 指定的间隔时间 单位ms
     */
    Subscription bindViewClickButEmitOnlyFirstInDuration(View view, Action1<? super Void> onNext, int duration);

}
