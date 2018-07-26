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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.sxenon.pure.core.component.IComponent;
import com.sxenon.pure.core.global.IntentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Inspired by https://github.com/k0shk0sh/PermissionHelper
 * Created by Sui on 2016/12/2.
 */

public class PermissionCompat {
    /**
     * be aware as it might return null (do check if the returned result is not null!)
     * <p/>
     * can be used outside of activity.
     */
    @Nullable
    public static String getFirstDeclinedPermission(@NonNull IComponent router, @NonNull String[] permissions) {
        for (String permission : permissions) {
            if (isPermissionDeclined(router, permission)) {
                return permission;
            }
        }
        return null;
    }

    /**
     * @return list of permissions that the user declined or not yet granted.
     */
    public static String[] getDeclinedPermissionArray(@NonNull IComponent router, @NonNull String[] permissions) {
        return (String[]) getDeclinedPermissionList(router, permissions).toArray();
    }

    public static List<String> getDeclinedPermissionList(@NonNull IComponent router, @NonNull String[] permissions) {
        List<String> permissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (isPermissionDeclined(router, permission) && isPermissionExisted(router, permission)) {
                permissionsNeeded.add(permission);
            }
        }
        return permissionsNeeded;
    }

    /**
     * return true if permission is granted, false otherwise.
     * <p/>
     * can be used outside of router.
     */
    public static boolean isPermissionGranted(@NonNull IComponent router, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(router.getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * return true if permission is declined, false otherwise.
     * <p/>
     * can be used outside of router.
     */
    public static boolean isPermissionDeclined(@NonNull IComponent router, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(router.getContext(), permission) != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * @return true if explanation needed.
     */
    public static boolean isExplanationNeeded(@NonNull IComponent router, @NonNull String permissionName) {
        return router.shouldShowRequestPermissionRationale(permissionName);
    }

    /**
     * @return true if the permission is patently denied by the user and only can be granted via settings Screen
     * <p/>
     * consider using {@link IntentManager#getViewApplicationDetailsIntent(Context)} to open settings screen
     */
    public static boolean isPermissionPermanentlyDenied(@NonNull IComponent router, @NonNull String permission) {
        return isPermissionDeclined(router, permission) && !isExplanationNeeded(router, permission);
    }

    public static List<String> getPermissionPermanentlyDeniedList(@NonNull IComponent router, @NonNull String[] permissions) {
        List<String> permissionPermanentlyDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            if (isPermissionPermanentlyDenied(router, permission)) {
                permissionPermanentlyDeniedList.add(permission);
            }
        }
        return permissionPermanentlyDeniedList;
    }

    /**
     * @return true if permission exists in the manifest, false otherwise.
     */
    public static boolean isPermissionExisted(@NonNull IComponent router, @NonNull String permission) {
        Context context = router.getContext();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (packageInfo.requestedPermissions != null) {
                for (String p : packageInfo.requestedPermissions) {
                    if (p.equals(permission)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return true if {@link android.Manifest.permission#SYSTEM_ALERT_WINDOW} is granted
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isSystemAlertGranted(@NonNull Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(context);
    }

    public static boolean isSystemAlertGranted(@NonNull IComponent router) {
        return isSystemAlertGranted(router.getContext());
    }

}
