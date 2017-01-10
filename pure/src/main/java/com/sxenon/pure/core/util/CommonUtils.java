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

package com.sxenon.pure.core.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import java.util.List;
import java.util.UUID;

/**
 * Created by Sui on 2016/12/24.
 */

public class CommonUtils {

    public static boolean isAppOnForeground(Context context) {
        // Returns a list of application processes that are running on the device
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    public static void setViewVisibility(View view,int visibility){
        if (view!=null){
            view.setVisibility(visibility);
        }
    }

    // Reto Meier used in the Google I/O presentation 2014
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    /**
     * MAC addresses are globally unique, not user-resettable and survive factory reset.
     * It is generally not recommended to use MAC address for any form of user identification.
     * As a result, as of Android M, local device MAC addresses (for example, Wifi and Bluetooth) are not available via third party APIs.
     * The WifiInfo.getMacAddress() method and the BluetoothAdapter.getDefaultAdapter().getAddress() method will both return 02:00:00:00:00:00..
     */
    public synchronized static String id(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.apply();
            }
        }
        return uniqueID;
    }
}
