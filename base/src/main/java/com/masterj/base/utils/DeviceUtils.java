package com.masterj.base.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.masterj.base.MasterJBase;
import com.masterj.base.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class DeviceUtils {

    private static final String TAG = "DeviceUtils";
    private static boolean isActivityStatusBarWhite = false;

    public static String getPackageName() {
        return getAppContext().getPackageName();
    }

    private static Context getAppContext() {
        return MasterJBase.baseDelegate.getApplication();
    }

    public static void initWindow(Activity activity) {
        if (activity != null) {
            initWindow(activity.getWindow());
        }
    }

    public static void initWindow(Window window) {
        if (window != null && canChangeStatusBar()) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility()
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static boolean isImmersiveMode(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS & activity.getWindow().getAttributes().flags)
                    == WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS &&
                    activity.getWindow().getStatusBarColor() == Color.TRANSPARENT;
        }
        return false;
    }

    /**
     * 尝试设置状态栏黑色字体图标
     * 适配部分MIUI、Flyme和Android6.0以上版本
     */
    public static boolean setStatusBarLightMode(Window window) {
        if (window != null && canChangeStatusBar()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getMIUIVersion() < 9) { // MIUI9之后才支持系统方法设置。https://dev.mi.com/doc/p=10416/index.html
                    return MIUISetStatusBarLightMode(window, true);
                }
                window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                return true;
            } else {
                return MIUISetStatusBarLightMode(window, true) || FlymeSetStatusBarLightMode(window, true);
            }
        }
        return false;
    }

    /**
     * 尝试设置状态栏白色字体图标
     * 适配部分MIUI、Flyme和Android6.0以上版本
     */
    public static boolean setStatusBarDarkMode(Window window) {
        if (window != null && canChangeStatusBar()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getMIUIVersion() < 9) { // MIUI9之后才支持系统方法设置。https://dev.mi.com/doc/p=10416/index.html
                    return MIUISetStatusBarLightMode(window, false);
                }
                window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                return true;
            } else {
                return MIUISetStatusBarLightMode(window, false) || FlymeSetStatusBarLightMode(window, false);
            }
        }
        return false;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static void setStatusBarReplacer(Context context, View rootView) {
        if (context instanceof Activity) {
            setStatusBarReplacer((Activity) context, rootView, true);
        }
    }

    /**
     * @param activity
     * @param rootView
     * @param isWhite  true 表示淡色主题，状态栏字体颜色为黑色
     */
    public static void setStatusBarReplacer(Activity activity, View rootView, boolean isWhite) {
        if (activity != null) {
            isActivityStatusBarWhite = isWhite;
            setStatusBarReplacer(activity, activity.getWindow(), rootView, isWhite, R.id.status_bar_replacer);
        }
    }

    /**
     * @param activity
     * @param rootView
     * @param isWhite  true 表示淡色主题，状态栏字体颜色为黑色
     */
    public static void setStatusBarReplacer(Activity activity, View rootView, boolean isWhite, int statusBarReplacerResId) {
        if (activity != null) {
            isActivityStatusBarWhite = isWhite;
            setStatusBarReplacer(activity, activity.getWindow(), rootView, isWhite, statusBarReplacerResId);
        }
    }

    /**
     * @param dialog
     * @param rootView
     */
    public static void setStatusBarReplacer(Dialog dialog, View rootView) {
        setStatusBarReplacer(dialog, rootView, isActivityStatusBarWhite);
    }

    /**
     * @param dialog
     * @param rootView
     * @param isWhite  true 表示淡色主题，状态栏字体颜色为黑色
     */
    public static void setStatusBarReplacer(Dialog dialog, View rootView, boolean isWhite) {
        if (dialog != null) {
            setStatusBarReplacer(dialog.getContext(), dialog.getWindow(), rootView, isWhite, R.id.status_bar_replacer);
        }
    }

    /**
     * @param context
     * @param window
     * @param rootView
     * @param isWhite  true 表示淡色主题，状态栏字体颜色为黑色
     */
    private static void setStatusBarReplacer(Context context, Window window, View rootView, boolean isWhite, int statusBarReplacerResId) {
        if (context == null) {
            return;
        }
        if (canChangeStatusBar()) {
            View statusBarReplacer = null;
            if (rootView != null) {
                statusBarReplacer = rootView.findViewById(statusBarReplacerResId);
                if (statusBarReplacer != null) {
                    ViewGroup.LayoutParams layoutParams = statusBarReplacer.getLayoutParams();
                    layoutParams.height = ScreenDimenHelper.getStatusBarHeight(context);
                    statusBarReplacer.setLayoutParams(layoutParams);
                }
            }
            if (window != null) {
                if (isWhite) {
                    if (!DeviceUtils.setStatusBarLightMode(window)) {
                        if (statusBarReplacer != null) {
                            statusBarReplacer.setBackgroundColor(ContextCompat.getColor(context, R.color.status_bar_default));
                        } else {
                            window.setStatusBarColor(ContextCompat.getColor(context, R.color.status_bar_default));
                        }
                    }
                } else {
                    DeviceUtils.setStatusBarDarkMode(window);
                }
            }
        }
    }

    private static String getSystemProperty(String propName) {
        String line = null;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (Throwable e) {
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Throwable e) {
                }
            }
        }
        return line;
    }

    /**
     * @return MIUI版本，非MIUI返回 Integer.MAX_VALUE
     */
    public static int getMIUIVersion() {
        String version = getSystemProperty("ro.miui.ui.version.name");
        if (StringUtils.isNotBlank(version)) {
            try {
                return Integer.valueOf(version.substring(1));
            } catch (Throwable e) {
            }
        }
        return Integer.MAX_VALUE;
    }

    public static boolean isOppoFindX() {
        String oppoMarket = getSystemProperty("ro.oppo.market.name");
        if (StringUtils.isBlank(oppoMarket)) {
            return false;
        }
        return "OPPO Find X".equalsIgnoreCase(oppoMarket.trim());
    }

    public static boolean isActivityStatusBarWhite() {
        return isActivityStatusBarWhite;
    }

    /**
     * 由于华为emui3.x系列的顶部状态栏现有方法无法实现沉浸式，emui3.X有兼容性问题，无法被隐藏。所以在设置沉浸式状态栏的方法前添加判断，不对emui3.x处理
     *
     * @return 能否进行沉浸式状态栏修改
     */
    public static boolean canChangeStatusBar() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !isHuaweiEmui3x();
    }

    public static boolean isHuaweiEmui3x() {
        Class<?> classType = null;
        String buildVersion = null;
        try {
            classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", new Class<?>[]{String.class});
            buildVersion = (String) getMethod.invoke(classType, new Object[]{"ro.build.version.emui"});//EmotionUI_5.1
            if (TextUtils.isEmpty(buildVersion)) {
                return false;
            }
            if (buildVersion.startsWith("EmotionUI_3.") || buildVersion.startsWith("EmotionUI 3.") || buildVersion.equals("EmotionUI 3")) {
                return true;
            }
        } catch (Throwable e) {
        }
        return false;
    }

    public static String getOperator() {
        String operatorName = "unknown";
        try {
            TelephonyManager tm = ((TelephonyManager) MasterJBase.baseDelegate.getApplication()
                    .getSystemService(Context.TELEPHONY_SERVICE));

            String IMSI = tm != null ? tm.getNetworkOperator() : "";
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46004") || IMSI.startsWith("46007")) {
                operatorName = "mobile";
            } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006") || IMSI.startsWith("46009")) {
                operatorName = "unicom";
            } else if (IMSI.startsWith("46003") || IMSI.startsWith("46005") || IMSI.startsWith("460011")) {
                operatorName = "telecom";
            }
        } catch (Exception e) {
        }

        return operatorName;
    }
}
