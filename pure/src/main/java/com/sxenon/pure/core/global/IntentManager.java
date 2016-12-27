package com.sxenon.pure.core.global;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;

import com.hwangjr.rxbus.RxBus;
import com.sxenon.pure.core.router.IRouter;

import java.io.File;
import java.util.List;

/**
 * 跳转路由
 * Intent的属性如下
 * Extra相关的，通过{@link RxBus},因为有些数据类型不太适合用Bundle来传递
 * 其他：Component、Action、Category、Data(Authority,Path,Scheme,SchemeSpecificPart,Type)、Flag
 * 以及Event本身和相对应的Tag，统一在一个路由里面处理
 * <p>
 * 可以把一些系统跳转放在这里
 * Created by Sui on 2016/11/24.
 */

public class IntentManager {
    public static Intent getOpenDialIntent(String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + tel);
        intent.setData(data);
        return intent;
    }

    public static Intent getOpenBrowserIntent(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        return intent;
    }

    public static Intent getInstallAppIntent(File appFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.fromFile(appFile);
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        return intent;
    }

    public static void openSettingsScreen(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.parse("package:" + context.getPackageName());
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void openSettingsScreen(IRouter router) {
        openSettingsScreen(router.getActivityCompact());
    }

    public static Intent getSettingWireLessIntent() {
        return new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
    }

    public static void openMarket(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (isIntentAvailable(context, intent)) {
            context.startActivity(intent);
        }
    }

    public static void openMarket(IRouter router) {
        openMarket(router.getActivityCompact());
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        List<ResolveInfo> resolves = context.getPackageManager().queryIntentActivities(intent, 0);
        return resolves.size() > 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestSystemAlertPermission(IRouter router, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + router.getActivityCompact().getPackageName()));
        router.startActivityForResult(intent, requestCode);
    }

}
