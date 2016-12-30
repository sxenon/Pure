package com.sxenon.pure.core.binder;

import android.view.View;

import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;

import rx.Observable;

/**
 * {@link ViewBinderImpl}
 * Dynamic Proxy
 * {@link BaseRootPresenter#getViewBinder()}} or {@link BasePresenter#getRootViewBinder()}
 * Created by Sui on 2016/10/26.
 */

@SuppressWarnings("UnusedReturnValue")
public interface IViewBinder {
    /**
     * 在默认的间隔内只执行第一次
     *
     * @param view   被点击的view
     */
    Observable<Void> bindViewClickButEmitOnlyFirstInDuration(View view);

    /**
     * 在指定的间隔内只执行第一次
     *
     * @param view     被点击的view
     * @param duration 指定的间隔时间 单位ms
     */
    Observable<Void> bindViewClickButEmitOnlyFirstInDuration(View view,int duration);

}
