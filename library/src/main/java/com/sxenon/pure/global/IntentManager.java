package com.sxenon.pure.global;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.hwangjr.rxbus.RxBus;
import com.sxenon.pure.core.IRouter;

import java.io.File;

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

    public static void openSettingsScreen(IRouter router){
        openSettingsScreen(router.getActivity());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestSystemAlertPermission(IRouter router,int requestCode){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + router.getActivity().getPackageName()));
        router.startActivityForResult(intent, requestCode);
    }

    /**
     * @return true if {@link android.Manifest.permission#SYSTEM_ALERT_WINDOW} is granted
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isSystemAlertGranted(@NonNull Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(context);
    }

    public static boolean isSystemAlertGranted(@NonNull IRouter router){
        return isSystemAlertGranted(router.getActivity());
    }

}
