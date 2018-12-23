package com.teddy.sweatcontacts.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.teddy.sweatcontacts.common.base.BaseViewModel
import com.teddy.sweatcontacts.common.base.FavoriteManager
import com.teddy.sweatcontacts.common.view.Status
import com.teddy.sweatcontacts.model.Contact
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeContactViewModel(
    private val repository: HomeContactRepository,
    private val favoriteManager: FavoriteManager,
    mainDispatcher: CoroutineContext
) : BaseViewModel(mainDispatcher) {

    private val _contactSearch = MutableLiveData<List<Contact>>()
    val contactSearch
        get() = _contactSearch as LiveData<List<Contact>>

    val favorites get() = favoriteManager.favorites

    val contacts = repository.listenResult

    fun fetchContacts() {
        launch(context = coroutineContext) { repository.getContacts() }
    }

    fun doContactSearchWith(query: String) {
        if (contacts.value?.status == Status.SUCCESS || contacts.value?.status == Status.SUCCESS_MORE) {
            val matchedContacts = contacts.value?.data?.filter { it.fullName.contains(query, true) }
            _contactSearch.value = matchedContacts
        }
    }

    fun fetchMoreContacts() {
        launch(context = coroutineContext) { repository.getMoreContacts() }
    }

    fun fetchFavorites() {
        favoriteManager.fetchFavorites()
    }
}