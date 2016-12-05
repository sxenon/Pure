package com.sxenon.pure.permission;

import android.support.annotation.NonNull;

public interface OnRequestPermissionsResult {

    void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults);

    void onActivityForResult(int requestCode);
}
