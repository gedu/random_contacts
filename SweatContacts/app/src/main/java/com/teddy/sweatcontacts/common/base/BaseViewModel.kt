package com.teddy.sweatcontacts.common.base

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(private val dispatcher: CoroutineContext) : ViewModel() , CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + dispatcher

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}