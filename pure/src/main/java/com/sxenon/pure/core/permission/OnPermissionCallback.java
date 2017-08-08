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

package com.sxenon.pure.core.permission;

import android.support.annotation.NonNull;

import rx.functions.Action0;

public interface OnPermissionCallback {

    void onPermissionGranted(Action0 action);

    void onPermissionDeclined(int requestCode, @NonNull String[] permissions);

    void onPermissionPermanentlyDeclined(int requestCode, @NonNull String[] permissions);

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean shouldExplainPermissionBeforeRequest(int requestCode, String[] permissions);
}
