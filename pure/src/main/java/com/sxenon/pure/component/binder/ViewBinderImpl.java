package com.sxenon.pure.component.binder;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Sui on 2016/10/25.
 */

public class ViewBinderImpl implements IViewBinder {

    private static final int DEFAULT_SKIP_DURATION =500;

    @Override
    public Subscription bindViewClickButEmitOnlyFirst(View view, Action1<? super Void> onNext) {
        return bindViewClickButEmitOnlyFirstInDuration(view,onNext,DEFAULT_SKIP_DURATION);
    }

    public Subscription bindViewClickButEmitOnlyFirstInDuration(View view, Action1<? super Void> onNext, int duration) {
        return RxView.clicks(view).throttleFirst(duration,TimeUnit.MILLISECONDS).subscribe(onNext);
    }
}
