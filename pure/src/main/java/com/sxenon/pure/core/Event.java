package com.sxenon.pure.core;

import android.os.Bundle;
import android.os.Message;

import com.hwangjr.rxbus.RxBus;

/**
 * Defines a event containing a description and arbitrary data object that can be
 * sent by a {@link RxBus}
 * It`s a lower-cost alternatives to using {@link Message}
 * Created by Sui on 2016/10/22.
 */

public class Event {
    public int what;
    public Object obj;
    public int arg1;
    public String arg2;
    public Bundle data;
}
