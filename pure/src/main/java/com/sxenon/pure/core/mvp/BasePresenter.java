package com.sxenon.pure.core.mvp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.binder.IViewBinder;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;

import rx.functions.Action0;

/**
 * Normal presenter should not has its child presenter,or it will be too complex.
 * Created by Sui on 2016/11/22.
 */

public class BasePresenter<VM extends IViewModule> implements IPresenter<VM> {
    private final VM mViewModule;
    private final Context mContext;
    private final IRouter mRouter;
    private BaseRootPresenter mRootPresenter;

    public BasePresenter(VM viewModule) {
        mViewModule = viewModule;
        mContext=mViewModule.getContext();
        mRouter=mViewModule.getRouter();
    }

    @Override
    public BaseRootPresenter getRootPresenter() {
        return mRootPresenter;
    }

    public void bindRootPresenter(BaseRootPresenter rootPresenter){
        if (mRootPresenter!=null){
            mRootPresenter=rootPresenter;
        }else {
            throw new IllegalStateException("This presenter is a root presenter,or has already bind one");
        }
    }

    @Override
    public VM getViewModule() {
        return mViewModule;
    }

    @NonNull
    @Override
    public Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public IRouter getRouter() {
        return mRouter;
    }

    public void requestCommonPermissionsViaRoot(@NonNull String[] permissions, int requestCode, Action0 action){
        mRootPresenter.requestCommonPermissions(requestCode, permissions, action);
    }

    public void requestSystemAlertPermissionViaRoot(int requestCode, Action0 action){
        mRootPresenter.requestSystemAlertPermission(requestCode, action);
    }

    public IViewBinder getRootViewBinder(){
        return mRootPresenter.getViewBinder();
    }
}
