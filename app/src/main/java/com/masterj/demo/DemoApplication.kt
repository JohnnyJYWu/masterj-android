package com.masterj.demo

import android.app.Application
import android.content.Context
import com.masterj.base.MasterJBaseInitHelper
import com.masterj.base.utils.MultiLanguageUtil

/**
 * Created by Johnny Wu on 2023/8/9
 */
class DemoApplication : Application() {
    private lateinit var context: Context
    private lateinit var application: DemoApplication

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        application = this
        MasterJBaseInitHelper.initMasterJBase(this)
    }

    override fun attachBaseContext(base: Context?) {
        // 系统语言等设置发生改变时会调用此方法，需要重置app语言
        base?.let {
            super.attachBaseContext(MultiLanguageUtil.attachBaseContext(it))
        }
    }
}
