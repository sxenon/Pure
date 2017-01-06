/*
 * Copyright (c) 2017 sxenon
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

package com.sxenon.pure.core.mvp.sub;

import android.support.annotation.NonNull;

import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;
import com.trello.rxlifecycle.LifecycleTransformer;

import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import rx.functions.Action0;

/**
 * Optional,for those complex {@link BaseRootPresenter}s
 * Created by Sui on 2016/11/22.
 */

public abstract class BaseSubPresenter<VM extends BaseSubViewModule> extends BasePresenter<VM> {
    private final BaseRootPresenter mRootPresenter;

    public BaseSubPresenter(VM viewModule,BaseRootPresenter rootPresenter) {
        super(viewModule);
        mRootPresenter = rootPresenter;
    }

    @Override
    public BaseRootPresenter getRootPresenter() {
        return mRootPresenter;
    }

    public void requestCommonPermissions(@NonNull String[] permissions, int requestCode, Action0 action) {
        mRootPresenter.requestCommonPermissions(permissions, requestCode, action);
    }

    public void requestSystemAlertPermission(int requestCode, Action0 action) {
        mRootPresenter.requestSystemAlertPermission(requestCode, action);
    }

    public void setOnKeyboardShowingListener(KeyboardUtil.OnKeyboardShowingListener listener) {
        mRootPresenter.setOnKeyboardShowingListener(listener);
    }

    @Override
    public <T> LifecycleTransformer<T> autoUnsubscribe() {
        //noinspection unchecked
        return mRootPresenter.autoUnsubscribe();
    }

//    @Override
//    public void registerActionOnDestroy(Action0 action) {
//        mRootPresenter.registerActionOnDestroy(action);
//    }
//
//    @Override
//    public void registerActionOnResume(Action0 action, boolean once) {
//        mRootPresenter.registerActionOnResume(action,once);
//    }
//
//    @Override
//    public void registerActionOnPause(Action0 action, boolean once) {
//        mRootPresenter.registerActionOnPause(action, once);
//    }
//
//    @Override
//    public void registerActionOnStop(Action0 action, boolean once) {
//        mRootPresenter.registerActionOnStop(action,once);
//    }
}
