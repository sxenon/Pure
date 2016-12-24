package com.sxenon.pure.global;

import android.annotation.SuppressLint;
import android.app.Application;

import com.sxenon.pure.core.Event;

import java.util.List;

/**
 * Save global context
 * Created by Sui on 2016/12/2.
 */

public enum GlobalContext {
    @SuppressLint("StaticFieldLeak")
    INSTANCE;

    public Application value;
    public ActivityHistoryManager activityHistoryManager =new ActivityHistoryManager();
    public List<Event> savedEventList;
}
