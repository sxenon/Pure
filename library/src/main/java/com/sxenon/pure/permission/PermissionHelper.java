package com.sxenon.pure.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.IRouter;
import com.sxenon.pure.global.IntentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Inspire from https://github.com/k0shk0sh/PermissionHelper
 * Created by Sui on 2016/12/2.
 */

public class PermissionHelper implements OnActivityPermissionCallback {
    private static final int OVERLAY_PERMISSION_REQ_CODE = 1117;
    private static final int REQUEST_PERMISSIONS = 1;

    @NonNull
    private final OnPermissionCallback permissionCallback;
    @NonNull private final IRouter router;
    private boolean forceAccepting;


    private PermissionHelper(@NonNull IRouter router, @NonNull OnPermissionCallback permissionCallback) {
        this.router = router;
        this.permissionCallback = permissionCallback;
    }
    
    @NonNull public static PermissionHelper getInstance(@NonNull IRouter router, @NonNull OnPermissionCallback permissionCallback) {
        return new PermissionHelper(router, permissionCallback);
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (verifyPermissions(grantResults)) {
                permissionCallback.onPermissionGranted(permissions);
            } else {
                String[] declinedPermissions = PermissionCompat.declinedPermissions(router, permissions);
                List<Boolean> deniedPermissionsLength = new ArrayList<>();//needed
                for (String permissionName : declinedPermissions) {
                    if (permissionName != null && !PermissionCompat.isExplanationNeeded(router,permissionName)) {
                        permissionCallback.onPermissionReallyDeclined(permissionName);
                        deniedPermissionsLength.add(false);
                    }
                }
                if (deniedPermissionsLength.size() == 0) {
                    if (forceAccepting) {
                        requestAfterExplanation(declinedPermissions);
                        return;
                    }
                    permissionCallback.onPermissionDeclined(declinedPermissions);
                }
            }
        }
    }

    /**
     * used only for {@link android.Manifest.permission#SYSTEM_ALERT_WINDOW}
     */
    @Override public void onActivityForResult(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
                if (PermissionCompat.isSystemAlertGranted(router)) {
                    permissionCallback.onPermissionGranted(new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW});
                } else {
                    permissionCallback.onPermissionDeclined(new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW});
                }
            }
        } else {
            permissionCallback.onPermissionPreGranted(Manifest.permission.SYSTEM_ALERT_WINDOW);
        }
    }

    /**
     * force the user to accept the permission. it won't work if the user ever thick-ed the "don't show again"
     */
    @NonNull public PermissionHelper setForceAccepting(boolean forceAccepting) {
        this.forceAccepting = forceAccepting;
        return this;
    }

    /**
     * @param permissionName
     *         (it can be one of these types (String), (String[])
     */
    @NonNull public PermissionHelper request(@NonNull Object permissionName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionName instanceof String) {
                handleSingle((String) permissionName);
            } else if (permissionName instanceof String[]) {
                handleMulti((String[]) permissionName);
            } else {
                throw new IllegalArgumentException("Permissions can only be one of these types (String) or (String[])" +
                        ". given type is " + permissionName.getClass().getSimpleName());
            }
        } else {
            permissionCallback.onNoPermissionNeeded();
        }
        return this;
    }

    /**
     * used only for {@link android.Manifest.permission#SYSTEM_ALERT_WINDOW}
     */
    public void requestSystemAlertPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (!PermissionCompat.isSystemAlertGranted(router)) {
                    IntentManager.requestSystemAlertPermission(router,OVERLAY_PERMISSION_REQ_CODE);
                } else {
                    permissionCallback.onPermissionPreGranted(Manifest.permission.SYSTEM_ALERT_WINDOW);
                }
            } catch (Exception ignored) {}
        } else {
            permissionCallback.onPermissionPreGranted(Manifest.permission.SYSTEM_ALERT_WINDOW);
        }
    }

    /**
     * internal usage.
     */
    private void handleSingle(@NonNull String permissionName) {
        if (PermissionCompat.permissionExists(router,permissionName)) {// android M throws exception when requesting
            // run time permission that does not exists in AndroidManifest.
            if (!permissionName.equalsIgnoreCase(Manifest.permission.SYSTEM_ALERT_WINDOW)) {
                if (PermissionCompat.isPermissionDeclined(router,permissionName)) {
                    if (PermissionCompat.isExplanationNeeded(router,permissionName)) {
                        permissionCallback.onPermissionNeedExplanation(permissionName);
                    } else {
                        router.requestPermissionsCompact(new String[]{permissionName}, REQUEST_PERMISSIONS);
                    }
                } else {
                    permissionCallback.onPermissionPreGranted(permissionName);
                }
            } else {
                requestSystemAlertPermission();
            }
        } else {
            permissionCallback.onPermissionDeclined(new String[]{permissionName});
        }
    }

    /**
     * internal usage.
     */
    private void handleMulti(@NonNull String[] permissionNames) {
        List<String> permissions = PermissionCompat.declinedPermissionsAsList(router, permissionNames);
        if (permissions.isEmpty()) {
            permissionCallback.onPermissionGranted(permissionNames);
            return;
        }
        boolean hasAlertWindowPermission = permissions.contains(Manifest.permission.SYSTEM_ALERT_WINDOW);
        if (hasAlertWindowPermission) {
            int index = permissions.indexOf(Manifest.permission.SYSTEM_ALERT_WINDOW);
            permissions.remove(index);
        }
        router.requestPermissionsCompact(permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSIONS);
    }

    /**
     * to be called when explanation is presented to the user
     */
    public void requestAfterExplanation(@NonNull String permissionName) {
        if (PermissionCompat.isPermissionDeclined(router,permissionName)) {
            router.requestPermissionsCompact(new String[]{permissionName}, REQUEST_PERMISSIONS);
        } else {
            permissionCallback.onPermissionPreGranted(permissionName);
        }
    }

    /**
     * to be called when explanation is presented to the user
     */
    public void requestAfterExplanation(@NonNull String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permissionName : permissions) {
            if (PermissionCompat.isPermissionDeclined(router,permissionName)) {
                permissionsToRequest.add(permissionName); // add permission to request
            } else {
                permissionCallback.onPermissionPreGranted(permissionName); // do not request, since it is already granted
            }
        }
        if (permissionsToRequest.isEmpty()) return;
        permissions = permissionsToRequest.toArray(new String[permissionsToRequest.size()]);
        router.requestPermissionsCompact(permissions, REQUEST_PERMISSIONS);
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
