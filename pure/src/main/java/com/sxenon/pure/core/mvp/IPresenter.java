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

package com.sxenon.pure.core.mvp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;
import com.trello.rxlifecycle.LifecycleTransformer;

import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import rx.functions.Action0;

/**
 * Presenter For Mvp
 * Created by Sui on 2016/11/21.
 */

public interface IPresenter<VM extends IViewModule> {
    VM getViewModule();

    @NonNull
    Context getContext();

    @NonNull
    IRouter getRouter();

    BaseRootPresenter getRootPresenter();

    void requestCommonPermissions(@NonNull String[] permissions, int requestCode, Action0 action);

    void requestSystemAlertPermission(int requestCode, Action0 action);

    void setOnKeyboardShowingListener(KeyboardUtil.OnKeyboardShowingListener listener);

    <T> LifecycleTransformer<T> autoUnsubscribe();

//    void registerActionOnDestroy(Action0 action);
//
//    void registerActionOnResume(Action0 action,boolean once);
//
//    void registerActionOnPause(Action0 action,boolean once);
//
//    void registerActionOnStop(Action0 action,boolean once);
}
