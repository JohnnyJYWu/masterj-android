package com.masterj.demo

import android.os.Bundle
import com.masterj.base.activity.BaseViewActivity
import com.masterj.demo.databinding.ActivityMainBinding

class MainActivity : BaseViewActivity<ActivityMainBinding>() {

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun needImmerse() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
