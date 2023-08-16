package com.masterj.base.utils

import android.content.Context
import android.content.SharedPreferences

object SpUtil {
    private const val NAME = "sp_config.cfg"
    private var sp: SharedPreferences? = null

    fun saveString(ctx: Context, key: String?, value: String?) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        }
        sp!!.edit().putString(key, value).commit()
    }

    fun getString(ctx: Context, key: String?): String {
        if (sp == null) {
            sp = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        }
        return sp!!.getString(key, "") ?: ""
    }
}
