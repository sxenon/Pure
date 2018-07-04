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
import android.support.annotation.NonNull;

import com.sxenon.pure.core.mvp.IPresenter;
import com.sxenon.pure.core.permission.OnPermissionCallback;

/**
 * Visitor for IRouter
 * Created by Sui on 2017/2/5.
 */

public interface IRouterVisitor<R extends IRouter> extends IPresenter<R>, OnPermissionCallback {
    boolean onBackPressed();

    //Use ViewModel instead
    //List<Event> getEventForSave();

    void handleActivityResult(int requestCode, int resultCode, Intent data);

    void requestPermissions(@NonNull String[] permissions, int requestCode, Runnable runnable, boolean forceAccepting);

    void requestSystemAlertPermission(int requestCode, Runnable runnable);

    void requestPermissionsAfterExplanation(@NonNull String[] permissions);
}
