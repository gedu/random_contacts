package com.teddy.sweatcontacts.home

import com.teddy.sweatcontacts.common.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeContactViewModel(
    private val repository: HomeContactRepository,
    mainDispatcher: CoroutineContext
) : BaseViewModel(mainDispatcher) {

    val contacts = repository.listenResult

    fun fetchContacts() {
        launch(context = coroutineContext) { repository.getContacts() }
    }
}