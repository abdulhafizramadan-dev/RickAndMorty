package com.gojek.base.utils

import android.view.View
import androidx.annotation.IdRes
import com.kennyc.view.MultiStateView

fun MultiStateView.showLoading() {
    viewState = MultiStateView.ViewState.LOADING
}

fun MultiStateView.showContent() {
    viewState = MultiStateView.ViewState.CONTENT
}

fun MultiStateView.showError() {
    viewState = MultiStateView.ViewState.ERROR
}

fun MultiStateView.showEmpty() {
    viewState = MultiStateView.ViewState.EMPTY
}

fun <ViewType : View> MultiStateView.getView(@IdRes id: Int): ViewType? {
    return getView(viewState)?.findViewById(id)
}
