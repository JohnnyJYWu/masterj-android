package com.masterj.base.utils

import android.content.Context
import java.util.*

object LocaleUtils {
    private const val LANGUAGE_EN = "en"
    private const val LANGUAGE_CN = "cn"
    const val LANGUAGE_JP = "jp"
    private const val LANGUAGE_AR = "ar"
    const val LOCALE_LANGUAGE_CHINESE = "zh"
    const val LOCALE_LANGUAGE_ENGLISH = "en"
    const val LOCALE_LANGUAGE_JAPANESE = "ja"
    const val LOCALE_LANGUAGE_KOREAN = "ko"
    const val LOCALE_LANGUAGE_HINDI = "hi"
    const val LOCALE_LANGUAGE_INDONESIAN = "in"
    const val LOCALE_LANGUAGE_MALAY = "ms"
    const val LOCALE_LANGUAGE_THAI = "th"
    const val LOCALE_LANGUAGE_VIETNAMESE = "vi"
    const val LOCALE_LANGUAGE_ARABIC = "ar"

    @JvmStatic
    fun getLanguageStr(context: Context): String {
        val language = MultiLanguageUtil.getAppLocale(context)?.language
        if (language == LOCALE_LANGUAGE_CHINESE) return LANGUAGE_CN
        return language ?: LANGUAGE_EN
    }

    @JvmStatic
    fun getRegion(context: Context): String {
        return MultiLanguageUtil.getAppLocale(context)?.country ?: ""
    }

    @JvmStatic
    fun getTimeDiff(): Int {
        return TimeZone.getDefault().rawOffset / 1000
    }

    fun isLanguageJP(context: Context): Boolean {
        return getLanguageStr(context) == LANGUAGE_JP
    }

    fun isLanguageCN(context: Context): Boolean {
        return getLanguageStr(context) == LANGUAGE_CN
    }

    fun isLanguageEN(context: Context): Boolean {
        return getLanguageStr(context) == LANGUAGE_EN
    }

    fun isLanguageAR(context: Context): Boolean {
        return getLanguageStr(context) == LANGUAGE_AR
    }
}
