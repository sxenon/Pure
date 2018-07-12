/*
 * Copyright (c) 2017  sxenon
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

package com.sj.pure.demo.verificationcode;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;

import com.sxenon.pure.core.result.handler.ISubmitResultHandler;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.router.IRouterVisitor;
import com.sxenon.pure.core.viewmodule.submitter.ISubmitterViewModule;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * @param <T> Data to require code
 * @param <R> Result of code require
 *            <p>
 *            Created by Sui on 2017/8/13.
 */

public abstract class RxVerificationCodeViewModule<T, R> implements ISubmitResultHandler<R>, ISubmitterViewModule<T> {
    private final int mSecondsInFuture;
    private final CountDownListener mCountDownListener;
    private final IRouter mRouter;

    public RxVerificationCodeViewModule(IRouter router, int secondsInFuture, CountDownListener countDownListener) {
        mSecondsInFuture = secondsInFuture;
        mCountDownListener = countDownListener;
        mRouter = router;
    }

    @Override
    public Context getContext() {
        return mRouter.getContext();
    }

    protected void startCountDown() {
        final IRouterVisitor routerVisitor = mRouter.getPresenter();
        //noinspection unchecked

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(mSecondsInFuture + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return mSecondsInFuture - aLong;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mCountDownListener.onStart();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        if (isRouterOnActive(routerVisitor)) {
                            mCountDownListener.onTick(aLong);
                        } else {
                            dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        if (isRouterOnActive(routerVisitor)) {
                            mCountDownListener.onFinish();
                        }
                    }
                });

    }

    private boolean isRouterOnActive(IRouterVisitor routerVisitor) {
        //TODO
        return true;
        //return routerVisitor.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

}
