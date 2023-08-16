package com.masterj.base

import android.app.Application

/**
 * Created by Johnny Wu on 2023/8/9
 */
class MasterJBaseInitHelper {
    companion object {
        @JvmStatic
        fun initAriesBase(application: Application) {
            MasterJBase.baseDelegate = object : MasterJBase.BaseDelegate {
                override fun getApplication(): Application {
                    return application
                }
            }
        }
    }
}
