package com.masterj.base.ui

sealed class UIState {
    object Loading : UIState()
    object Success : UIState()
    object Error : UIState()
    object Empty : UIState()
}
