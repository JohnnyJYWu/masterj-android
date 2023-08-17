package com.masterj.base

import android.app.Application

/**
 * Created by Johnny Wu on 2023/8/9
 */
object MasterJBase {
    lateinit var baseDelegate: BaseDelegate

    interface BaseDelegate {
        fun getApplication(): Application
    }

    @JvmStatic
    fun getApplication(): Application {
        return baseDelegate.getApplication()
    }

    fun registerActivityLifeCycleCallback(activityLifecycleCallbacks: Application.ActivityLifecycleCallbacks) {
        getApplication().registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }
}
