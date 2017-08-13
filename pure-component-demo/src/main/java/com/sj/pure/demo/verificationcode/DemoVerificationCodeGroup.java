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

import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.sj.pure.demo.RequestBean;
import com.sj.pure.demo.ResponseBean;
import com.sj.pure.demo.pull.R;
import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.mvp.IView;

/**
 * Demo for VerificationCodeGroup
 * Created by Sui on 2017/8/14.
 */

public class DemoVerificationCodeGroup extends RxVerificationCodeGroup<RequestBean,ResponseBean> {
    private static final String TAG="Demo";
    private final IView mContainer;

    public DemoVerificationCodeGroup(final IView container, int secondsInFuture, final Button codeButton, final TextView countDownText){
        super(container.getRouter(), secondsInFuture, new CountDownListener() {
            @Override
            public void onStart() {
                codeButton.setEnabled(false);//在发送数据的时候设置为不能点击
                codeButton.setBackgroundColor(Color.GRAY);//背景色设为灰色
            }

            @Override
            public void onTick(long secondsUntilFinished) {
                countDownText.setText(String.valueOf(secondsUntilFinished));
            }

            @Override
            public void onFinish() {
                countDownText.setText(container.getContext().getResources().getString(R.string.app_name));//随便填的，编译通过就行
                codeButton.setEnabled(true);
                codeButton.setBackgroundColor(Color.parseColor("#f97e7e"));
            }
        });
        mContainer=container;
    }

    @Override
    public void onSubmitFailed(ResponseBean result) {
        Log.i(TAG,"onSubmitFailed,result="+result.toString());
    }

    @Override
    public RequestBean getDataForSubmit() {
        return new RequestBean();
    }

    @Override
    public void onCancel() {
        doSomething();
    }

    private void doSomething(){
        Log.i(TAG,mContainer.toString());
    }

    @Override
    public void onException(ApiException exception) {

    }
}
