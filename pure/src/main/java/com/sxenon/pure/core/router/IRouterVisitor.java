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

package com.sxenon.pure.core.router;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.IPresenter;
import com.sxenon.pure.core.permission.OnPermissionCallback;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.List;

import rx.functions.Action0;

/**
 * Visitor for IRouter
 * Created by Sui on 2017/2/5.
 */

interface IRouterVisitor<R extends IRouter> extends IPresenter<R>, ILifecycle, LifecycleProvider<RouterEvent>, OnPermissionCallback {
    boolean onBackPressed();

    List<Event> getEventForSave();

    RouterEvent getCurrentEvent();

    void handleActivityResult(int requestCode, int resultCode, Intent data);

    void requestCommonPermissions(@NonNull String[] permissions, int requestCode, Action0 action);

    void requestSystemAlertPermission(int requestCode, Action0 action);

    void requestPermissionsAfterExplanation(@NonNull String[] permissions);

    <T> LifecycleTransformer<T> autoComplete();

    void onConfigurationChanged(Configuration newConfig);
}
