package com.masterj.base.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import java.util.*

object MultiLanguageUtil {
    private const val LOCALE_LANGUAGE = "locale_language"

    fun attachBaseContext(context: Context): Context {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createConfigurationResources(context)
        } else {
            setConfiguration(context)
            context
        }
    }

    /**
     * 设置语言
     *
     * @param context
     */
    private fun setConfiguration(context: Context) {
        val configuration = context.resources.configuration
        configuration.setLocale(getStoreLocale(context))
        val resources = context.resources
        // 语言更换生效的代码!
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun createConfigurationResources(context: Context): Context {
        val configuration = context.resources.configuration
        configuration.setLocale(getStoreLocale(context))
        return context.createConfigurationContext(configuration)
    }

    // 获取本地存储的语言信息，如果本地没有使用默认Locale
    private fun getStoreLocale(context: Context): Locale? {
        val appLocale = getAppLocale(context)
        val spLanguage: String = getStoreLanguage(context)
        return if (spLanguage.isNotEmpty()) {
            if (appLocale?.language == spLanguage) {
                appLocale
            } else {
                getLocaleByLanguage(context, spLanguage)
            }
        } else {
            appLocale
        }
    }

    /**
     * 更改应用语言
     */
    fun changeAppLanguage(context: Context, language: String) {
        val resources = context.resources
        val metrics = resources.displayMetrics
        val configuration = resources.configuration
        val locale = getLocaleByLanguage(context, language)
        configuration.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(configuration)
        }
        resources.updateConfiguration(configuration, metrics)
    }

    // 获取应用的实际的语言信息
    fun getAppLocale(context: Context): Locale? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            context.resources.configuration.locale
        }
    }

    // 判断是否需要修改app语言
    fun needChangeAppLanguage(context: Context): Boolean {
        return getStoreLanguage(context).isNotEmpty() &&
            getAppLocale(context)?.language != getStoreLanguage(context)
    }

    fun getStoreLanguage(context: Context): String {
        return SpUtil.getString(context, LOCALE_LANGUAGE)
    }

    fun saveLanguage(context: Context, language: String) {
        SpUtil.saveString(context, LOCALE_LANGUAGE, language)
    }

    private fun getLocaleByLanguage(context: Context, language: String): Locale {
        return Locale.Builder()
            .setLanguage(language)
            .setRegion(LocaleUtils.getRegion(context))
            .setScript(if (language == LocaleUtils.LOCALE_LANGUAGE_CHINESE) "Hans" else "")
            .build()
    }
}
