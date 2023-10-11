package com.masterj.base.arch

import com.masterj.base.data.BaseData
import com.masterj.base.ui.UIState

data class PageData<T : BaseData>(
    val pageState: UIState = UIState.Loading,
    val pageData: T
) : BaseData()

data class EventData<T : BaseData>(
    val event: T? = null
) : BaseData()

object EmptyBaseData : BaseData()
