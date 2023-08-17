package com.masterj.base.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.masterj.base.runtime.MasterJRuntime

/**
 * Created by Johnny Wu on 2023/8/17
 */
class GlobalActivityLifecycleCallback : Application.ActivityLifecycleCallbacks {

    private var runningActivityCount = 0

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        runningActivityCount++
        if (runningActivityCount == 1) { // 进入前台
            MasterJRuntime.isAppInBackground = false
        }
    }

    override fun onActivityResumed(activity: Activity) {
        MasterJRuntime.isAppInBackground = false
        MasterJRuntime.setCurrentActivity(activity)
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        runningActivityCount--
        if (runningActivityCount == 0) { // 切入后台
            MasterJRuntime.isAppInBackground = true
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }
}
