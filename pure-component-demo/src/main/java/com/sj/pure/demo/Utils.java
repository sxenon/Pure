/*
 * Copyright (c) 2017  sxenon
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

package com.sj.pure.demo;

import android.app.Activity;

/**
 * Demo
 * Created by Sui on 2017/8/14.
 */

public class Utils {
    private static long pressedTime = 0;

    public static void checkAgainForExit(Activity activity, long cdMills) {
        if ((System.currentTimeMillis() - pressedTime) > cdMills) {
            //Toast.makeText(activity, "Press again and exit!", Toast.LENGTH_SHORT).show();
            pressedTime = System.currentTimeMillis();
            return;
        }
        activity.finish();
    }
}
