package com.sxenon.pure.permission;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.sxenon.pure.core.IRouter;
import com.sxenon.pure.global.IntentManager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Sui on 2016/12/2.
 */

public class PermissionCompat {
    /**
     * be aware as it might return null (do check if the returned result is not null!)
     * <p/>
     * can be used outside of activity.
     */
    @Nullable
    public static String declinedPermission(@NonNull IRouter router, @NonNull String[] permissions) {
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
    public static String[] declinedPermissions(@NonNull IRouter router, @NonNull String[] permissions) {
        return (String[]) declinedPermissionsAsList(router, permissions).toArray();
    }

    public static List<String> declinedPermissionsAsList(@NonNull IRouter router, @NonNull String[] permissions) {
        List<String> permissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (isPermissionDeclined(router, permission) && permissionExists(router, permission)) {
                permissionsNeeded.add(permission);
            }
        }
        return permissionsNeeded;
    }

    /**
     * return true if permission is granted, false otherwise.
     * <p/>
     * can be used outside of activity.
     */
    public static boolean isPermissionGranted(@NonNull IRouter router, @NonNull String permission) {
        return ActivityCompat.checkSelfPermission(router.getFragmentActivity(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * return true if permission is declined, false otherwise.
     * <p/>
     * can be used outside of activity.
     */
    public static boolean isPermissionDeclined(@NonNull IRouter router, @NonNull String permission) {
        return ActivityCompat.checkSelfPermission(router.getFragmentActivity(), permission) != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * @return true if explanation needed.
     */
    public static boolean isExplanationNeeded(@NonNull IRouter router, @NonNull String permissionName) {
        return ActivityCompat.shouldShowRequestPermissionRationale(router.getFragmentActivity(), permissionName);
    }

    /**
     * @return true if the permission is patently denied by the user and only can be granted via settings Screen
     * <p/>
     * consider using {@link IntentManager#openSettingsScreen(Context)} to open settings screen
     */
    public static boolean isPermissionPermanentlyDenied(@NonNull IRouter router, @NonNull String permission) {
        return isPermissionDeclined(router, permission) && !isExplanationNeeded(router, permission);
    }

    /**
     * @return true if permission exists in the manifest, false otherwise.
     */
    public static boolean permissionExists(@NonNull IRouter router, @NonNull String permissionName) {
        try {
            PackageInfo packageInfo = router.getFragmentActivity().getPackageManager().getPackageInfo(router.getFragmentActivity().getPackageName(), PackageManager.GET_PERMISSIONS);
            if (packageInfo.requestedPermissions != null) {
                for (String p : packageInfo.requestedPermissions) {
                    if (p.equals(permissionName)) {
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

    public static boolean isSystemAlertGranted(@NonNull IRouter router){
        return isSystemAlertGranted(router.getFragmentActivity());
    }



}
