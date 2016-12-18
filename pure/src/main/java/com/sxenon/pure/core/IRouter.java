package com.sxenon.pure.core;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxenon.pure.core.mvp.root.BaseRootPresenter;

/**
 * Treat Activity & Fragment as router
 * Created by Sui on 2016/11/20.
 */

public interface IRouter<P extends BaseRootPresenter> {

    void startActivity(Intent intent);

    void startActivity(Intent intent, Bundle options);

    void startActivityForResult(Intent intent, int requestCode);

    void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options);

    Activity getActivityCompact();

    void requestPermissionsCompact(@NonNull String[] permissions);

    boolean shouldShowRequestPermissionRationale(String permission);

    void saveEvent(Event event);

    P getRootPresenter();

    /**
     * @return true,if the router is an activity,otherwise a fragment;
     */
    boolean isActivity();

}
