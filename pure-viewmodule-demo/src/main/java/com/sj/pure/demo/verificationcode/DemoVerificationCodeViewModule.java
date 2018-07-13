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
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.result.handler.IResultHandler;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.usecase.UseCase;
import com.sxenon.pure.core.usecase.UseCaseHandler;
import com.sxenon.pure.core.viewmodule.IViewModule;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Demo for VerificationCodeViewHolder
 * Created by Sui on 2017/8/14.
 */

public class DemoVerificationCodeViewModule implements IViewModule, IResultHandler {
    private static final String TAG = "Demo";
    private final Button mCodeBtn;
    private final TextView mCountDownTv;
    private final IRouter mRouter;
    private final int mSecondsInFuture;

    public DemoVerificationCodeViewModule(IRouter router, int secondsInFuture, final Button codeBtn, final TextView countDownTv) {

        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountDown();
            }
        });
        mRouter = router;
        mCodeBtn = codeBtn;
        mCountDownTv = countDownTv;
        mSecondsInFuture = secondsInFuture;
    }


    private void startCountDown() {
        UseCaseHandler.getInstance().execute(new RxVerificationCodeUseCase(), new RxVerificationCodeUseCase.RequestValues(mSecondsInFuture), new UseCase.UseCaseCallback<RxVerificationCodeUseCase.ResponseValue>() {
            @Override
            public void onSuccess(RxVerificationCodeUseCase.ResponseValue response) {
                switch (response.getState()) {
                    case START:
                        break;
                    case FINISH:
                        break;
                    case TICkING:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(ApiException apiException) {
                onApiException(apiException);
            }
        });
    }

    @Override
    public Context getContext() {
        return mCodeBtn.getContext();
    }


    @Override
    public void onCancel() {

    }

    @Override
    public void onApiException(ApiException apiException) {
        mCountDownTv.setText(apiException.toString());
        mCodeBtn.setEnabled(true);
        mCodeBtn.setBackgroundColor(Color.parseColor("#f97e7e"));
        //Suppose it is a network exception;
        mRouter.startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), 1);
    }
}
