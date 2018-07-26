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
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.global.IntentManager;
import com.sxenon.pure.core.component.IComponent;

import java.util.List;

/**
 * Inspired by https://github.com/k0shk0sh/PermissionHelper
 * Created by Sui on 2016/12/2.
 */

public class PermissionHelper {
    private Event permissionEvent;
    @NonNull
    private final OnPermissionCallback permissionCallback;
    @NonNull
    private final IComponent component;
    private final String KEY_FORCE_ACCEPTING = "forceAccepting";

    public PermissionHelper(@NonNull IComponent component, @NonNull OnPermissionCallback permissionCallback) {
        this.component = component;
        this.permissionCallback = permissionCallback;
    }

    public void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
        if (verifyPermissions(grantResults)) {
            permissionCallback.onPermissionGranted((Runnable) permissionEvent.obj);
        } else {
            String[] declinedPermissions = PermissionCompat.getDeclinedPermissionArray(component, permissions);
            List<String> permissionPermanentlyDeniedList = PermissionCompat.getPermissionPermanentlyDeniedList(component, declinedPermissions);
            if (!permissionPermanentlyDeniedList.isEmpty()) {
                permissionCallback.onPermissionPermanentlyDeclined(permissionEvent.what, (String[]) permissionPermanentlyDeniedList.toArray());
            } else {
                if (permissionEvent.data.getBoolean(KEY_FORCE_ACCEPTING, false)) {
                    if (!permissionCallback.shouldExplainPermissionBeforeRequest(permissionEvent.what, declinedPermissions)) {
                        component.requestPermissionsCompact(declinedPermissions, permissionEvent.what, (Runnable) permissionEvent.obj, permissionEvent.data.getBoolean(KEY_FORCE_ACCEPTING, false));
                    }
                    return;
                }
                permissionCallback.onPermissionDeclined(permissionEvent.what, declinedPermissions);
            }
        }
        permissionEvent = null;
    }

    /**
     * used only for {@link android.Manifest.permission#SYSTEM_ALERT_WINDOW}
     */
    public void onRequestSystemAlertPermissionResult(int resultCode) {
        if (Activity.RESULT_OK == resultCode) {
            permissionCallback.onPermissionGranted((Runnable) permissionEvent.obj);
        } else {
            permissionCallback.onPermissionDeclined(permissionEvent.what, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW});
        }
        permissionEvent = null;
    }

    public Event getPermissionEvent() {
        return permissionEvent;
    }

    public void setPermissionEvent(int what, Runnable runnable, boolean forceAccepting) {
        permissionEvent = new Event();
        permissionEvent.what = what;
        permissionEvent.obj = runnable;
        permissionEvent.data = new Bundle();
        permissionEvent.data.putBoolean(KEY_FORCE_ACCEPTING, forceAccepting);
    }

    public void requestPermissions(@NonNull String[] permissions, int what, Runnable runnable, boolean forceAccepting) {
        List<String> permissionsNeeded = PermissionCompat.getDeclinedPermissionList(component, permissions);
        if (permissionsNeeded.isEmpty()) {
            runnable.run();
            return;
        }

        String[] permissionsNeedArray = (String[]) permissionsNeeded.toArray();
        List<String> permissionPermanentlyDeniedList = PermissionCompat.getPermissionPermanentlyDeniedList(component, permissionsNeedArray);
        if (!permissionPermanentlyDeniedList.isEmpty()) {
            permissionCallback.onPermissionPermanentlyDeclined(what, permissions);
            return;
        }

        setPermissionEvent(what, runnable, forceAccepting);
        if (!permissionCallback.shouldExplainPermissionBeforeRequest(what, permissionsNeedArray)) {
            component.requestPermissionsCompact(permissionsNeedArray, what, runnable, forceAccepting);
        }
    }

    /**
     * used only for {@link android.Manifest.permission#SYSTEM_ALERT_WINDOW}
     *
     * @return Return true if isSystemAlertGranted already;
     */
    @TargetApi(Build.VERSION_CODES.M)
    public boolean showSystemAlertAtOnce(int what, Runnable runnable) {
        if (!PermissionCompat.isSystemAlertGranted(component)) {
            permissionEvent = new Event();
            permissionEvent.what = what;
            permissionEvent.obj = runnable;
            IntentManager.requestSystemAlertPermission(component, what);
            return false;
        } else {
            runnable.run();
            return true;
        }
    }

    /**
     * to be called when explanation is presented to the user
     */
    public void requestPermissionsAfterExplanation(@NonNull String[] permissions) {
        component.requestPermissionsCompact(permissions, permissionEvent.what, (Runnable) permissionEvent.obj, permissionEvent.data.getBoolean(KEY_FORCE_ACCEPTING, false));
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
