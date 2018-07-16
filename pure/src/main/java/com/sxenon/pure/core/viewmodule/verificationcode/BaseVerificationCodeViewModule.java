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

package com.sxenon.pure.core.viewmodule.verificationcode;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.sxenon.pure.core.result.handler.IResultHandler;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.viewmodule.IViewModule;

/**
 * Demo for VerificationCodeViewHolder
 * Created by Sui on 2017/8/14.
 * See Demo
 */

public abstract class BaseVerificationCodeViewModule implements IViewModule, IResultHandler {
    private static final String TAG = "Demo";
    private final Button mCodeBtn;
    private final TextView mCountDownTv;
    private final IRouter mRouter;

    public BaseVerificationCodeViewModule(IRouter router, final Button codeBtn, final TextView countDownTv) {
        mRouter = router;
        mCodeBtn = codeBtn;
        mCountDownTv = countDownTv;
    }

    /*
        点了发送验证码的按钮，成功发送，才会启动冷却。所以是被动的
     */
    public abstract void startCountDown(int secondsInFuture);


    public IRouter getRouter() {
        return mRouter;
    }

    public Button getCodeBtn() {
        return mCodeBtn;
    }

    public TextView getCountDownTv() {
        return mCountDownTv;
    }

    @Override
    public Context getContext() {
        return mCodeBtn.getContext();
    }

    @Override
    public void onCancel() {

    }

}
