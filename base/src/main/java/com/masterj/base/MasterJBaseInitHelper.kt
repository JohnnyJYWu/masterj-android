package com.masterj.base

import android.app.Application
import com.masterj.base.lifecycle.GlobalActivityLifecycleCallback

/**
 * Created by Johnny Wu on 2023/8/9
 */
class MasterJBaseInitHelper {
    companion object {
        @JvmStatic
        fun initMasterJBase(application: Application) {
            MasterJBase.baseDelegate = object : MasterJBase.BaseDelegate {
                override fun getApplication(): Application {
                    return application
                }
            }

            MasterJBase.registerActivityLifeCycleCallback(GlobalActivityLifecycleCallback())
        }
    }
}
