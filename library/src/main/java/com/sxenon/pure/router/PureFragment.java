package com.sxenon.pure.router;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.IRouter;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;

/**
 * TODO RxFragment研究
 * Created by Sui on 2016/11/21.
 */

public abstract class PureFragment<P extends PureRootPresenter> extends Fragment implements IRouter<P> {
    private Event mSavedEvent;
    private P mRootPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //noinspection unchecked
        mRootPresenter= (P) new BaseRootPresenter<>(groupViewModule());
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
        mSavedEvent=event;
    }
}
