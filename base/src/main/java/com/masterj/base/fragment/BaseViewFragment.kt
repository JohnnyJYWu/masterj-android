package com.masterj.base.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by Johnny Wu on 2023/8/9
 */
abstract class BaseViewFragment<B : ViewBinding> : Fragment() {
    lateinit var binding: B
        private set

    var isViewDestroy = true
        private set

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewDestroy = false
        binding = createBinding(view)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isViewDestroy = true
    }

    abstract fun createBinding(view: View): B
}
