package com.sxenon.pure.router;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.view.View;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.IRouter;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;

/**
 * 做最纯净的Fragment二次封装
 * Created by Sui on 2016/11/21.
 */

public abstract class PureFragment<P extends PureRootPresenter> extends Fragment implements IRouter<P> {
    private Event mSavedEvent;
    private P mRootPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseRootViewModule<P> rootViewModule = groupViewModule(view);
        mRootPresenter = rootViewModule.getPresenter();
        mRootPresenter.onCreate(mSavedEvent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRootPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRootPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mRootPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootPresenter.onDestroy();
    }

    @Override
    public P getRootPresenter() {
        return mRootPresenter;
    }

    @Override
    public void saveEvent(Event event) {
        mSavedEvent = event;
    }

    @Override
    public Activity getActivityCompact() {
        return getActivity();
    }

    @Override
    public void requestPermissionsCompact(@NonNull String[] permissions, int requestCode) {
        FragmentCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mRootPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected abstract BaseRootViewModule<P> groupViewModule(View view);

}
