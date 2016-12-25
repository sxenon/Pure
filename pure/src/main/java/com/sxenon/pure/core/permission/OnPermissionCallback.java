package com.sxenon.pure.core.permission;

import android.support.annotation.NonNull;

import rx.functions.Action0;

public interface OnPermissionCallback {

    void onPermissionGranted(@NonNull String[] permissions,Action0 action);

    void onPermissionDeclined(@NonNull String[] permissions,int permissionWhat);

    void onPermissionReallyDeclined(@NonNull String[] permissions,int permissionWhat);

    boolean shouldPermissionExplainBeforeRequest(String[] permissions, int permissionWhat);

}
