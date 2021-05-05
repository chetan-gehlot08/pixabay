package com.pixabay.util

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> FragmentActivity.withViewModel(body: T.() -> Unit): T {
    val vm = getViewModel<T>(this)
    vm.body()
    return vm
}
inline fun <reified T : ViewModel> getViewModel(activity: FragmentActivity): T {
    return ViewModelProvider(activity)[T::class.java]
}