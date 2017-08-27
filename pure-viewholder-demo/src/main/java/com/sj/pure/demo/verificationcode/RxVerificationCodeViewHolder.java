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

import android.content.Context;

import com.sxenon.pure.core.viewholder.submitter.ISubmitterViewHolder;
import com.sxenon.pure.core.result.handler.ISubmitResultHandler;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.router.IRouterVisitor;
import com.sxenon.pure.core.router.RouterEvent;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *
 * @param <T> Data to require code
 * @param <R> Result of code require
 *
 * Created by Sui on 2017/8/13.
 */

public abstract class RxVerificationCodeViewHolder<T,R> implements ISubmitResultHandler<R>,ISubmitterViewHolder<T> {
    private final int mSecondsInFuture;
    private final CountDownListener mCountDownListener;
    private final IRouter mRouter;

    public RxVerificationCodeViewHolder(IRouter router, int secondsInFuture, CountDownListener countDownListener){
        mSecondsInFuture =secondsInFuture;
        mCountDownListener=countDownListener;
        mRouter=router;
    }

    @Override
    public Context getContext() {
        return mRouter.getContext();
    }

    protected void startCountDown(){
        final IRouterVisitor routerVisitor=mRouter.getPresenter();
        //noinspection unchecked
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(mSecondsInFuture + 1)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return mSecondsInFuture - aLong;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mCountDownListener.onStart();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(routerVisitor.<Long>bindUntilEvent(RouterEvent.DESTROY))
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        if (isRouterOnActive(routerVisitor)){
                            mCountDownListener.onFinish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (isRouterOnActive(routerVisitor)){
                            mCountDownListener.onTick(aLong);
                        }
                    }
                });
    }

    private boolean isRouterOnActive(IRouterVisitor routerVisitor){
        return routerVisitor.getCurrentEvent()!= RouterEvent.DESTROY;
    }

}
