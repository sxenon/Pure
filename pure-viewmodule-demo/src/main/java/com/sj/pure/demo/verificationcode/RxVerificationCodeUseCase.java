/*
 * Copyright (c) 2018  sxenon
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

import com.sxenon.pure.core.viewmodule.verificationcode.BaseVerificationCodeUseCase;
import com.sxenon.pure.core.viewmodule.verificationcode.CountDownListener;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;

public class RxVerificationCodeUseCase extends BaseVerificationCodeUseCase {

    protected void startCountDown(final int secondsInFuture, final CountDownListener countDownListener) {

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(secondsInFuture + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return secondsInFuture - aLong;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        countDownListener.onStart();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        countDownListener.onTick(aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        countDownListener.onFinish();
                    }
                });
    }

}
