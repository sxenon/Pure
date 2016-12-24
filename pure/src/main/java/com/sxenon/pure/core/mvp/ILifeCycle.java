package com.sxenon.pure.core.mvp;

import com.sxenon.pure.core.Event;

import java.util.List;

/**
 * RootPresenter should hai it`s own lifeCycle callBack.
 * Created by Sui on 2016/11/21.
 */

public interface ILifeCycle {
    void onCreate(List<Event> savedEventList);

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
