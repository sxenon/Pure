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

import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sj.pure.demo.RequestBean;
import com.sj.pure.demo.ResponseBean;
import com.sj.pure.demo.pull.R;
import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.mvp.IView;

import rx.functions.Action1;

/**
 * Demo for VerificationCodeGroup
 * Created by Sui on 2017/8/14.
 */

public class DemoVerificationCodeGroup extends RxVerificationCodeGroup<RequestBean, ResponseBean> {
    private static final String TAG = "Demo";
    private final IView mContainer;
    private final Button mCodeBtn;
    private final TextView mCountDownTv;

    public DemoVerificationCodeGroup(final IView container, int secondsInFuture, final Button codeBtn, final TextView countDownTv, final Action1<View> requestCodeAction) {
        super(container.getRouter(), secondsInFuture, new CountDownListener() {
            @Override
            public void onStart() {
                codeBtn.setEnabled(false);//在发送数据的时候设置为不能点击
                codeBtn.setBackgroundColor(Color.GRAY);//背景色设为灰色
            }

            @Override
            public void onTick(long secondsUntilFinished) {
                countDownTv.setText(String.valueOf(secondsUntilFinished));
            }

            @Override
            public void onFinish() {
                countDownTv.setText(container.getContext().getResources().getString(R.string.app_name));//随便填的，编译通过就行
                codeBtn.setEnabled(true);
                codeBtn.setBackgroundColor(Color.parseColor("#f97e7e"));
            }
        });
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCodeAction.call(v);
            }
        });
        mContainer = container;
        mCodeBtn = codeBtn;
        mCountDownTv = countDownTv;
    }

    @Override
    public void onSubmitFailed(ResponseBean result) {
        Log.i(TAG, "onSubmitFailed,result=" + result.toString());
    }

    @Override
    public RequestBean getDataForSubmit() {
        return new RequestBean();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onException(ApiException exception) {
        mCountDownTv.setText(exception.getExceptionMsg());
        mCodeBtn.setEnabled(true);
        mCodeBtn.setBackgroundColor(Color.parseColor("#f97e7e"));
        //Suppose it is a network exception;
        mContainer.getRouter().startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS),1);
    }
}
