package com.sxenon.pure.core.router;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxenon.pure.core.Event;

import java.util.List;

/**
 * Treat Activity & Fragment as router
 * Created by Sui on 2016/11/20.
 */

public interface IRouter<P extends PureRootPresenter> {

    void startActivity(Intent intent);

    void startActivity(Intent intent, Bundle options);

    void startActivityForResult(Intent intent, int requestCode);

    void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options);

    Activity getActivityCompact();

    void requestPermissionsCompact(@NonNull String[] permissions);

    boolean shouldShowRequestPermissionRationale(String permission);

    void saveEventList(List<Event> eventList);

    P getRootPresenter();

    RouterType getRouterType();

    enum RouterType {
        ACTIVITY,
        FRAGMENT_ACTIVITY,
        SUPPORT_FRAGMENT,
        FRAGMENT,
    }
}
