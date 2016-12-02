package com.sxenon.pure.core;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;

import com.sxenon.pure.core.mvp.root.BaseRootPresenter;

/**
 * 部分透明化Activity与Fragment
 * Created by Sui on 2016/11/20.
 */

public interface IRouter<P extends BaseRootPresenter> {

    void startActivity(Intent intent);

    void startActivity(Intent intent, Bundle options);

    void startActivityForResult(Intent intent, int requestCode);

    void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options);

    FragmentActivity getFragmentActivity();

    @RequiresApi(api = Build.VERSION_CODES.M)
    void requestPermissions(@NonNull String[] permissions, int requestCode);

    void saveEvent(Event event);

    P getRootPresenter();
}
