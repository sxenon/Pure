package com.sxenon.pure.core.mvp.interfaces;

import com.sxenon.pure.core.Event;

/**
 * Created by Sui on 2016/11/21.
 */

public interface ILifeCycle {
    void onCreate(Event savedEvent);

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
