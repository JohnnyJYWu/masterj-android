package com.masterj.base.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.masterj.base.R
import java.io.BufferedReader
import java.io.InputStreamReader

internal object StatusBarUtils {

    fun initWindow(window: Window?) {
        if (window != null && canChangeStatusBar()) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = (
                window.decorView.systemUiVisibility
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * 由于华为emui3.x系列的顶部状态栏现有方法无法实现沉浸式，emui3.X有兼容性问题，无法被隐藏。所以在设置沉浸式状态栏的方法前添加判断，不对emui3.x处理
     * @return 能否进行沉浸式状态栏修改
     */
    private fun canChangeStatusBar(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !isHuaweiEmui3x()
    }

    private fun isHuaweiEmui3x(): Boolean {
        val classType: Class<*>?
        val buildVersion: String?
        try {
            classType = Class.forName("android.os.SystemProperties")
            val getMethod = classType.getDeclaredMethod("get", *arrayOf<Class<*>>(String::class.java))
            buildVersion = getMethod.invoke(classType, *arrayOf<Any>("ro.build.version.emui")) as String // EmotionUI_5.1
            if (TextUtils.isEmpty(buildVersion)) {
                return false
            }
            if (buildVersion.startsWith("EmotionUI_3.") || buildVersion!!.startsWith("EmotionUI 3.") || buildVersion == "EmotionUI 3") {
                return true
            }
        } catch (e: Throwable) {
        }
        return false
    }

    /**
     * @param activity
     * @param rootView
     * @param isWhite  true 表示淡色主题，状态栏字体颜色为黑色
     */
    fun setStatusBarReplacer(activity: Activity?, rootView: View?, isWhite: Boolean) {
        if (activity != null) {
            setStatusBarReplacer(activity, activity.window, rootView, isWhite, R.id.status_bar_replacer)
        }
    }

    /**
     * @param context
     * @param window
     * @param rootView
     * @param isWhite  true 表示淡色主题，状态栏字体颜色为黑色
     */
    private fun setStatusBarReplacer(context: Context?, window: Window?, rootView: View?, isWhite: Boolean, statusBarReplacerResId: Int) {
        if (context == null) {
            return
        }
        if (canChangeStatusBar()) {
            var statusBarReplacer: View? = null
            if (rootView != null) {
                statusBarReplacer = rootView.findViewById(statusBarReplacerResId)
                if (statusBarReplacer != null) {
                    val layoutParams = statusBarReplacer.layoutParams
                    layoutParams.height = getStatusBarHeight(context)
                    statusBarReplacer.layoutParams = layoutParams
                }
            }
            if (window != null) {
                if (isWhite) {
                    if (!setStatusBarLightMode(window)) {
                        if (statusBarReplacer != null) {
                            statusBarReplacer.setBackgroundColor(ContextCompat.getColor(context, R.color.status_bar_default))
                        } else {
                            window.statusBarColor = ContextCompat.getColor(context, R.color.status_bar_default)
                        }
                    }
                } else {
                    setStatusBarDarkMode(window)
                }
            }
        }
    }

    /**
     * 尝试设置状态栏白色字体图标
     * 适配部分MIUI、Flyme和Android6.0以上版本
     */
    private fun setStatusBarDarkMode(window: Window?): Boolean {
        return if (window != null && canChangeStatusBar()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getMIUIVersion() < 9) { // MIUI9之后才支持系统方法设置。https://dev.mi.com/doc/p=10416/index.html
                    return MIUISetStatusBarLightMode(window, false)
                }
                window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                true
            } else {
                MIUISetStatusBarLightMode(window, false) || FlymeSetStatusBarLightMode(window, false)
            }
        } else {
            false
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun FlymeSetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                value = if (dark) {
                    value or bit
                } else {
                    value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: java.lang.Exception) {
            }
        }
        return result
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun MIUISetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
        var result = false
        if (window != null) {
            val clazz: Class<*> = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag) // 状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag) // 清除黑色字体
                }
                result = true
            } catch (e: Exception) {
            }
        }
        return result
    }

    /**
     * 尝试设置状态栏黑色字体图标
     * 适配部分MIUI、Flyme和Android6.0以上版本
     */
    fun setStatusBarLightMode(window: Window?): Boolean {
        return if (window != null && canChangeStatusBar()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getMIUIVersion() < 9) { // MIUI9之后才支持系统方法设置。https://dev.mi.com/doc/p=10416/index.html
                    return MIUISetStatusBarLightMode(window, true)
                }
                window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                true
            } else {
                MIUISetStatusBarLightMode(window, true) || FlymeSetStatusBarLightMode(window, true)
            }
        } else {
            false
        }
    }

    /**
     * @return MIUI版本，非MIUI返回 Integer.MAX_VALUE
     */
    fun getMIUIVersion(): Int {
        val version: String = getSystemProperty("ro.miui.ui.version.name")
        if (version.isNotEmpty()) {
            try {
                return Integer.valueOf(version.substring(1))
            } catch (e: Throwable) {
            }
        }
        return Int.MAX_VALUE
    }

    private fun getSystemProperty(propName: String): String {
        var line = ""
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop $propName")
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (e: Throwable) {
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: Throwable) {
                }
            }
        }
        return line
    }

    private fun getStatusBarHeight(context: Context): Int {
        var result = 72 // 1920x1280
        val resourceId = context.resources
            .getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}
