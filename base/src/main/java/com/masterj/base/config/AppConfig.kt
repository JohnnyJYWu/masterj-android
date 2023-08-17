package com.masterj.base.config

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.masterj.base.MasterJBase

object AppConfig {

    fun getApplication(): Application {
        return MasterJBase.getApplication()
    }

    fun getPackageName(): String {
        return getApplication().packageName
    }

    fun getPackageManager(): PackageManager {
        return getApplication().packageManager
    }

    fun getAppInfo(): ApplicationInfo? {
        return try {
            getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
}
