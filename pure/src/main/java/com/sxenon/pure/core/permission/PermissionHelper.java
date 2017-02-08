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

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.global.IntentManager;

import java.util.Arrays;
import java.util.List;

import rx.functions.Action0;

/**
 * Inspired by https://github.com/k0shk0sh/PermissionHelper
 * Created by Sui on 2016/12/2.
 */

public class PermissionHelper {
    private Event permissionEvent;
    @NonNull
    private final OnPermissionCallback permissionCallback;
    @NonNull
    private final IRouter router;
    private boolean forceAccepting;

    private PermissionHelper(@NonNull IRouter router, @NonNull OnPermissionCallback permissionCallback) {
        this.router = router;
        this.permissionCallback = permissionCallback;
    }

    @NonNull
    public static PermissionHelper getInstance(@NonNull IRouter router, @NonNull OnPermissionCallback permissionCallback) {
        return new PermissionHelper(router, permissionCallback);
    }

    public void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
        if (verifyPermissions(grantResults)) {
            permissionCallback.onPermissionGranted((Action0) permissionEvent.obj);
        } else {
            String[] declinedPermissions = PermissionCompat.getDeclinedPermissionArray(router, permissions);
            List<String> permissionPermanentlyDeniedList = PermissionCompat.getPermissionPermanentlyDeniedList(router, declinedPermissions);
            if (!permissionPermanentlyDeniedList.isEmpty()) {
                permissionCallback.onPermissionReallyDeclined(permissionEvent.what, (String[]) permissionPermanentlyDeniedList.toArray());
            } else {
                if (forceAccepting) {
                    if (!permissionCallback.shouldPermissionExplainBeforeRequest(permissionEvent.what, declinedPermissions)) {
                        requestAfterExplanation(declinedPermissions);
                    }
                    return;
                }
                permissionCallback.onPermissionDeclined(permissionEvent.what, declinedPermissions);
            }
        }
    }

    /**
     * used only for {@link android.Manifest.permission#SYSTEM_ALERT_WINDOW}
     */
    public void onRequestSystemAlertPermissionResult(int resultCode) {
        if (Activity.RESULT_OK == resultCode) {
            permissionCallback.onPermissionGranted((Action0) permissionEvent.obj);
        } else {
            permissionCallback.onPermissionDeclined(permissionEvent.what, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW});
        }
    }

    /**
     * force the user to accept the permission. it won't work if the user ever thick-ed the "don't show again"
     */
    @NonNull
    public PermissionHelper setForceAccepting(boolean forceAccepting) {
        this.forceAccepting = forceAccepting;
        return this;
    }

    public void requestCommonPermissions(@NonNull String[] permissions, int what, Action0 action) {
        List<String> permissionsNeeded = PermissionCompat.getDeclinedPermissionList(router, permissions);
        if (permissionsNeeded.isEmpty()) {
            action.call();
            return;
        }
        Event event = new Event();
        event.what = what;
        event.obj = action;
        permissionEvent = event;
        String[] permissionsNeedArray = (String[]) permissionsNeeded.toArray();
        List<String> permissionPermanentlyDeniedList = PermissionCompat.getPermissionPermanentlyDeniedList(router, permissionsNeedArray);
        if (!permissionPermanentlyDeniedList.isEmpty()) {
            permissionCallback.onPermissionReallyDeclined(permissionEvent.what, permissions);
            return;
        }
        if (!permissionCallback.shouldPermissionExplainBeforeRequest(what, permissionsNeedArray)) {
            internalRequest(what, permissions);
        }
    }

    /**
     * used only for {@link android.Manifest.permission#SYSTEM_ALERT_WINDOW}
     *
     * @return Return true if isSystemAlertGranted already;
     */
    @TargetApi(Build.VERSION_CODES.M)
    public boolean showSystemAlertAtOnce(int what, Action0 action) {
        boolean actionAtOnce = false;
        if (!PermissionCompat.isSystemAlertGranted(router)) {
            Event event = new Event();
            event.what = what;
            event.obj = action;
            permissionEvent = event;
            IntentManager.requestSystemAlertPermission(router, what);
        } else {
            action.call();
            actionAtOnce = true;
        }
        return actionAtOnce;
    }

    /**
     * internal usage.
     */
    private void internalRequest(int requestCode, @NonNull String[] permissions) {
        if (Arrays.binarySearch(permissions, Manifest.permission.SYSTEM_ALERT_WINDOW) >= 0) {
            throw new IllegalArgumentException("Please Call showSystemAlertAtOnce() for SYSTEM_ALERT_WINDOW!");
        }
        router.requestPermissionsCompact(permissions, requestCode);
    }

    /**
     * to be called when explanation is presented to the user
     */
    public void requestAfterExplanation(@NonNull String[] permissions) {
        router.requestPermissionsCompact(permissions, permissionEvent.what);
    }

    /**
     * internal usage.
     */
    private boolean verifyPermissions(@NonNull int[] grantResults) {
        if (grantResults.length < 1) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
