package com.sxenon.pure.core.util;

import android.app.ActivityManager;
import android.content.Context;
import android.view.View;

import java.util.List;

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
}
