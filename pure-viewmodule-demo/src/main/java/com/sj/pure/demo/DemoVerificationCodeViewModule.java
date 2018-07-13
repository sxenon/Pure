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

package com.sj.pure.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

import com.sj.pure.demo.pull.R;
import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.result.handler.IResultHandler;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.usecase.UseCase;
import com.sxenon.pure.core.usecase.UseCaseHandler;
import com.sxenon.pure.core.viewmodule.IViewModule;
import com.sxenon.pure.core.viewmodule.verificationcode.RxVerificationCodeUseCase;

/**
 * Demo for VerificationCodeViewHolder
 * Created by Sui on 2017/8/14.
 * DataSource from Other
 */

public class DemoVerificationCodeViewModule implements IViewModule, IResultHandler {
    private static final String TAG = "Demo";
    private final Button mCodeBtn;
    private final TextView mCountDownTv;
    private final IRouter mRouter;
    private final int mSecondsInFuture;

    public DemoVerificationCodeViewModule(IRouter router, int secondsInFuture, final Button codeBtn, final TextView countDownTv) {
        mRouter = router;
        mCodeBtn = codeBtn;
        mCountDownTv = countDownTv;
        mSecondsInFuture = secondsInFuture;
    }

    /*
        点了发送验证码的按钮，成功发送，才会启动冷却。所以是被动的
     */
    public void startCountDown() {
        UseCaseHandler.getInstance().execute(new RxVerificationCodeUseCase(), new RxVerificationCodeUseCase.RequestValues(mSecondsInFuture), new UseCase.UseCaseCallback<RxVerificationCodeUseCase.ResponseValue>() {
            @Override
            public void onSuccess(RxVerificationCodeUseCase.ResponseValue response) {
                switch (response.getState()) {
                    case START:{
                        mCodeBtn.setEnabled(false);//在发送数据的时候设置为不能点击
                        mCodeBtn.setBackgroundColor(Color.GRAY);//背景色设为灰色
                    }
                        break;
                    case FINISH:{
                        mCountDownTv.setText(mRouter.getContext().getResources().getString(R.string.app_name));//随便填的，编译通过就行
                        mCodeBtn.setEnabled(true);
                        mCodeBtn.setBackgroundColor(Color.parseColor("#f97e7e"));
                    }
                        break;
                    case TICkING:{
                        mCountDownTv.setText(String.valueOf(response.getSecondsUntilFinished()));
                    }
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
