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

import android.os.CountDownTimer;

import com.sxenon.pure.core.viewholder.submitter.ISingleSubmitterViewHolder;
import com.sxenon.pure.core.result.ISubmitResultHandler;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.router.RouterEvent;

import rx.Observable;
import rx.Subscriber;

/**
 *
 *
 * @param <T> Data to require code
 * @param <R> Result of code require
 *
 * 不推荐：
 * 1、时间不到0问题
 * 2、刚开始跳时间
 * Created by Sui on 2017/8/13.
 */
@Deprecated
public abstract class VerificationCodeViewHolder<T,R> implements ISubmitResultHandler<R>,ISingleSubmitterViewHolder<T> {
    private CountDownTimer mCountDownTimer;
    private final long mMillisInFuture;
    private final CountDownListener mCountDownListener;

    public VerificationCodeViewHolder(IRouter router, long millisInFuture, CountDownListener countDownListener){
        mMillisInFuture=millisInFuture;
        mCountDownListener=countDownListener;
        //noinspection unchecked
        Observable.never().compose(router.getPresenter().bindUntilEvent(RouterEvent.DESTROY)).subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                    mCountDownTimer = null;
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    @Override
    public void onSubmitSuccess(R result) {
        mCountDownListener.onStart();
        if (mCountDownTimer==null){
            mCountDownTimer=new CountDownTimer(mMillisInFuture, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mCountDownListener.onTick(Math.round(millisUntilFinished/1000));
                }

                @Override
                public void onFinish() {
                    mCountDownListener.onFinish();
                }
            };
            mCountDownTimer.start();
        }
    }
}
