package com.masterj.base.runtime

import android.annotation.SuppressLint
import android.app.Activity

/**
 * Created by Johnny Wu on 2023/8/17
 */
@SuppressLint("StaticFieldLeak")
object MasterJRuntime {

    var isAppInBackground = true

    private var currentActivity: Activity? = null

    private val activityTasks = mutableListOf<String>()

    fun setCurrentActivity(activity: Activity?) {
        currentActivity = activity
    }

    fun getCurrentActivity(): Activity? {
        if (currentActivity?.isDestroyed == true || currentActivity?.isFinishing == true) {
            return null
        }
        return currentActivity
    }
}
