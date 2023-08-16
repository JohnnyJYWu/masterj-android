package com.masterj.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.masterj.base.MasterJBase;

import java.lang.reflect.Method;

public class ScreenDimenHelper {

    public static int getStatusBarHeight(Context context) {
        int result = 72; // 1920x1280
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getScreenHeight() {//获取屏幕尺寸，不包括虚拟按键高度
        WindowManager wm = (WindowManager) getAppContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) getAppContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getFullScreenHeight() {//获取屏幕原始尺寸高度（包括虚拟按键）
        int height = 0;
        WindowManager wm = (WindowManager) getAppContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        Class c;
        try {
            c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            height = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return height;
    }

    public static Context getAppContext() {
        return MasterJBase.baseDelegate.getApplication();
    }
}
