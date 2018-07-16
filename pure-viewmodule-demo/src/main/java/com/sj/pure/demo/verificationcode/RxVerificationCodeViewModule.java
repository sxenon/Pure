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

import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

import com.sj.pure.demo.pull.R;
import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.usecase.UseCase;
import com.sxenon.pure.core.usecase.UseCaseHandler;
import com.sxenon.pure.core.viewmodule.verificationcode.BaseVerificationCodeViewModule;

public class RxVerificationCodeViewModule extends BaseVerificationCodeViewModule {
    public RxVerificationCodeViewModule(IRouter router, Button codeBtn, TextView countDownTv) {
        super(router, codeBtn, countDownTv);
    }

    /*
        点了发送验证码的按钮，成功发送，才会启动冷却。所以是被动的
     */
    public void startCountDown(int secondsInFuture) {
        UseCaseHandler.getInstance().execute(new RxVerificationCodeUseCase(), new RxVerificationCodeUseCase.RequestValues(secondsInFuture), new UseCase.UseCaseCallback<RxVerificationCodeUseCase.ResponseValue>() {
            @Override
            public void onSuccess(RxVerificationCodeUseCase.ResponseValue response) {
                switch (response.getState()) {
                    case START:{
                        getCodeBtn().setEnabled(false);//在发送数据的时候设置为不能点击
                        getCodeBtn().setBackgroundColor(Color.GRAY);//背景色设为灰色
                    }
                    break;
                    case FINISH:{
                        getCountDownTv().setText(getRouter().getContext().getResources().getString(R.string.app_name));//随便填的，编译通过就行
                        getCodeBtn().setEnabled(true);
                        getCodeBtn().setBackgroundColor(Color.parseColor("#f97e7e"));
                    }
                    break;
                    case TICkING:{
                        getCountDownTv().setText(String.valueOf(response.getSecondsUntilFinished()));
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
    public void onApiException(ApiException apiException) {
        getCountDownTv().setText(apiException.toString());
        getCodeBtn().setEnabled(true);
        getCodeBtn().setBackgroundColor(Color.parseColor("#f97e7e"));
        //Suppose it is a network exception;
        getRouter().startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), 1);
    }
}
