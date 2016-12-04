package com.sxenon.pure.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.IRouter;
import com.sxenon.pure.global.IntentManager;

import java.util.Arrays;
import java.util.List;

import rx.functions.Action0;

/**
 * Inspire by https://github.com/k0shk0sh/PermissionHelper
 * Created by Sui on 2016/12/2.
 */

public class PermissionHelper implements OnRequestPermissionsResult {
    private static final int OVERLAY_PERMISSION_REQ_CODE = 1117;
    private static final int REQUEST_PERMISSIONS = 130;
    private Event permissionEvent;

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
                permissionCallback.onPermissionGranted(permissions, (Action0) permissionEvent.obj);
            } else {
                String[] declinedPermissions = PermissionCompat.getDeclinedPermissionArray(router, permissions);
                List<String> permissionPermanentlyDeniedList=PermissionCompat.getPermissionPermanentlyDeniedList(router,declinedPermissions);
                if (!permissionPermanentlyDeniedList.isEmpty()){
                    permissionCallback.onPermissionReallyDeclined((String[]) permissionPermanentlyDeniedList.toArray(),permissionEvent.what);
                }else{
                    if (forceAccepting) {
                        if (!permissionCallback.shouldPermissionExplainBeforeRequest(declinedPermissions,permissionEvent.what)){
                            requestAfterExplanation(declinedPermissions);
                        }
                        return;
                    }
                    permissionCallback.onPermissionDeclined(declinedPermissions,permissionEvent.what);
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
                    permissionCallback.onPermissionGranted(new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, (Action0) permissionEvent.obj);
                } else {
                    permissionCallback.onPermissionDeclined(new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},permissionEvent.what);
                }
            }
        }
    }

    /**
     * force the user to accept the permission. it won't work if the user ever thick-ed the "don't show again"
     */
    @NonNull public PermissionHelper setForceAccepting(boolean forceAccepting) {
        this.forceAccepting = forceAccepting;
        return this;
    }

    public void requestCommonPermissions(@NonNull String[] permissions, int what, Action0 action){
        List<String> permissionsNeeded= PermissionCompat.getDeclinedPermissionList(router,permissions);
        if (permissionsNeeded.isEmpty()){
            action.call();
            return;
        }
        Event event=new Event();
        event.what=what;
        event.obj=action;
        permissionEvent=event;
        String[] permissionsNeedArray=(String[]) permissionsNeeded.toArray();
        List<String> permissionPermanentlyDeniedList=PermissionCompat.getPermissionPermanentlyDeniedList(router,permissionsNeedArray);
        if (!permissionPermanentlyDeniedList.isEmpty()){
            permissionCallback.onPermissionReallyDeclined(permissions,permissionEvent.what);
            return;
        }
        if (!permissionCallback.shouldPermissionExplainBeforeRequest(permissionsNeedArray,what)){
            internalRequest(permissions);
        }
    }

    /**
     * used only for {@link android.Manifest.permission#SYSTEM_ALERT_WINDOW}
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void requestSystemAlertPermission(int what,Action0 action) {
        if (!PermissionCompat.isSystemAlertGranted(router)) {
            Event event=new Event();
            event.what=what;
            event.obj=action;
            permissionEvent=event;
            IntentManager.requestSystemAlertPermission(router,OVERLAY_PERMISSION_REQ_CODE);
        }else {
            action.call();
        }
    }

    /**
     * internal usage.
     */
    private void internalRequest(@NonNull String[] permissions) {
        if (Arrays.binarySearch(permissions,Manifest.permission.SYSTEM_ALERT_WINDOW)>=0){
            throw new IllegalArgumentException("Please Call requestSystemAlertPermission() for SYSTEM_ALERT_WINDOW!");
        }
        router.requestPermissionsCompact(permissions, REQUEST_PERMISSIONS);
    }

    /**
     * to be called when explanation is presented to the user
     */
    public void requestAfterExplanation(@NonNull String permissionName) {
        requestAfterExplanation(new String[]{permissionName});
    }

    /**
     * to be called when explanation is presented to the user
     */
    public void requestAfterExplanation(@NonNull String[] permissions) {
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
