package com.sxenon.pure.permission;

import android.support.annotation.NonNull;

import rx.functions.Action0;

public interface OnPermissionCallback {

    void onPermissionGranted(@NonNull String[] permissions,Action0 action);

    void onPermissionDeclined(@NonNull String[] permissions,int permissionWhat);

    void onPermissionReallyDeclined(@NonNull String[] permissions,int permissionWhat);

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean shouldPermissionExplainBeforeRequest(String[] permissions, int what);

}
