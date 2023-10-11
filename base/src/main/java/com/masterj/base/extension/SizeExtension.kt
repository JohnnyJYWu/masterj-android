package com.masterj.base.extension

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by Wu Jingyuan on 2023/9/18
 */
val Int.dp
    get() = this.toFloat().dp

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
