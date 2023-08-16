package com.masterj.base.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.masterj.base.utils.StatusBarUtils

/**
 * Created by Johnny Wu on 2023/8/9
 */
abstract class BaseViewActivity<B : ViewBinding> : FragmentActivity() {

    lateinit var binding: B
        private set

    var paused = false
        private set
    var stopped = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = createBinding()
        setContentView(binding.root)
        if (needImmerse()) {
            StatusBarUtils.initWindow(window)
            StatusBarUtils.setStatusBarReplacer(this, window.decorView, isWhiteStatusBar())
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        paused = false
    }

    override fun onPause() {
        super.onPause()
        paused = true
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun createBinding(): B

    open fun needImmerse(): Boolean = false

    open fun isWhiteStatusBar(): Boolean = false
}
