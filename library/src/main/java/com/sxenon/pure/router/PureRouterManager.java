package com.sxenon.pure.router;

import android.content.Intent;
import android.net.Uri;

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

public class PureRouterManager {
    public static void toDial(IRouter router, String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + tel);
        intent.setData(data);
        router.startActivity(intent);
    }

    public static void toBrowser(IRouter router, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        router.startActivity(intent);
    }

    public static void toInstallApp(IRouter router, File appFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.fromFile(appFile);
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        router.startActivity(intent);
    }
}
