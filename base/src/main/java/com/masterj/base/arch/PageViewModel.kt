package com.masterj.base.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masterj.base.data.BaseData
import com.masterj.base.ui.UIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class PageViewModel<T : BaseData, E : BaseData> : ViewModel() {

    /**
     * 初始状态
     * stateFlow区别于LiveData必须有初始值
     */
    private val initialData: PageData<T> by lazy { createInitialData() }

    abstract fun createInitialData(): PageData<T>

    private val stateFlow: MutableStateFlow<PageData<T>> = MutableStateFlow(initialData)
    private val sharedFlow: MutableSharedFlow<EventData<E>> = MutableSharedFlow()

    /**
     * 设置当前页面数据
     */
    fun setData(pageData: T) {
        viewModelScope.launch {
            stateFlow.emit(stateFlow.value.copy(pageData = pageData))
        }
    }

    /**
     * 设置当前页面状态
     */
    fun setPageState(pageState: UIState) {
        viewModelScope.launch {
            stateFlow.emit(stateFlow.value.copy(pageState = pageState))
        }
    }

    fun sendEvent(event: E) {
        viewModelScope.launch {
            sharedFlow.emit(EventData(event = event))
        }
    }

    /**
     * 获取页面的数据流
     */
    fun getDataFlow(): StateFlow<PageData<T>> = stateFlow

    /**
     * 获取页面数据
     */
    fun getPageData(): T = getDataFlow().value.pageData

    /**
     * 获取页面状态
     */
    fun getPageState(): UIState = getDataFlow().value.pageState

    /**
     * 获取页面的事件流
     */
    fun getEventFlow(): SharedFlow<EventData<E>> = sharedFlow
}
