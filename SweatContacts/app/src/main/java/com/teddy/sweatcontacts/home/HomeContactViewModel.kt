package com.teddy.sweatcontacts.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.teddy.sweatcontacts.common.base.BaseViewModel
import com.teddy.sweatcontacts.common.view.Status
import com.teddy.sweatcontacts.model.Contact
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeContactViewModel(
    private val repository: HomeContactRepository,
    mainDispatcher: CoroutineContext
) : BaseViewModel(mainDispatcher) {

    private val _contactSearch = MutableLiveData<List<Contact>>()
    val contactSearch
        get() = _contactSearch as LiveData<List<Contact>>

    val contacts = repository.listenResult

    fun fetchContacts() {
        launch(context = coroutineContext) { repository.getContacts() }
    }

    fun doContactSearchWith(query: String) {
        if (contacts.value?.status == Status.SUCCESS) {
            val matchedContacts = contacts.value?.data?.filter { it.fullName.contains(query, true) }
            _contactSearch.value = matchedContacts
        }
    }
}