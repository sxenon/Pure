package com.sxenon.pure.core.binder;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Dynamic proxy.
 * Created by Sui on 2016/10/25.
 */

public class ViewBinderImpl implements IViewBinder {

    private static final int DEFAULT_SKIP_DURATION =500;

    @Override
    public Observable<Void> bindViewClickButEmitOnlyFirstInDuration(View view) {
        return bindViewClickButEmitOnlyFirstInDuration(view,DEFAULT_SKIP_DURATION);
    }

    public Observable<Void> bindViewClickButEmitOnlyFirstInDuration(View view, int duration) {
        return RxView.clicks(view).throttleFirst(duration,TimeUnit.MILLISECONDS);
    }
}
