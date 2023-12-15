package com.masterj.base.utils

import android.os.Build
import android.os.Build.VERSION_CODES.KITKAT
import android.view.View

object SystemUIHelper {

    @JvmStatic
    fun setImmersiveMode(decorView: View?) {
        if (decorView == null) {
            return
        }
        if (Build.VERSION.SDK_INT >= KITKAT) {
            val systemUI = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

            decorView.systemUiVisibility = systemUI
        }
    }

    @JvmStatic
    fun setLayoutFullScreenMode(decorView: View?) {
        if (decorView == null) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val systemUI = (
                decorView.systemUiVisibility
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
            decorView.systemUiVisibility = systemUI
        }
    }
}
