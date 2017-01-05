/*
 * Copyright (c) 2017 sxenon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
