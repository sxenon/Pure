package com.sxenon.pure.core.permission;

import android.support.annotation.NonNull;

import rx.functions.Action0;

public interface OnPermissionCallback {

    void onPermissionGranted(Action0 action);

    void onPermissionDeclined(int requestCode, @NonNull String[] permissions);

    void onPermissionReallyDeclined(int requestCode, @NonNull String[] permissions);

    boolean shouldPermissionExplainBeforeRequest(int requestCode, String[] permissions);

}
