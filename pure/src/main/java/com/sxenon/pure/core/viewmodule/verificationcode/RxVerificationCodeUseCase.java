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

package com.sxenon.pure.core.viewmodule.verificationcode;

import com.sxenon.pure.core.usecase.UseCase;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;

public class RxVerificationCodeUseCase extends UseCase<RxVerificationCodeUseCase.RequestValues,RxVerificationCodeUseCase.ResponseValue> {

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        startCountDown(requestValues.getSecondsInFuture(), new CountDownListener() {
            @Override
            public void onStart() {
                getUseCaseCallback().onSuccess(new ResponseValue(CountState.START,requestValues.getSecondsInFuture()));
            }

            @Override
            public void onTick(long secondsUntilFinished) {
                getUseCaseCallback().onSuccess(new ResponseValue(CountState.TICkING,secondsUntilFinished));
            }

            @Override
            public void onFinish() {
                getUseCaseCallback().onSuccess(new ResponseValue(CountState.FINISH,0));
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues{
        private final int secondsInFuture;

        public RequestValues(int secondsInFuture) {
            this.secondsInFuture = secondsInFuture;
        }

        public int getSecondsInFuture() {
            return secondsInFuture;
        }
    }

    public enum CountState{
        START,
        TICkING,
        FINISH
    }

    public static class ResponseValue implements UseCase.ResponseValue{
        private final CountState state;
        private final long secondsUntilFinished;

        public ResponseValue(CountState state, long secondsUntilFinished) {
            this.state = state;
            this.secondsUntilFinished = secondsUntilFinished;
        }

        public CountState getState() {
            return state;
        }

        public long getSecondsUntilFinished() {
            return secondsUntilFinished;
        }
    }

    private void startCountDown(final int secondsInFuture, final CountDownListener countDownListener) {

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
