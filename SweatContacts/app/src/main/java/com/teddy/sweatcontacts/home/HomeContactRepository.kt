package com.teddy.sweatcontacts.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.teddy.sweatcontacts.common.network.ContactRemoteSource
import com.teddy.sweatcontacts.common.network.PaginationManager
import com.teddy.sweatcontacts.common.storage.LocalStorageSource
import com.teddy.sweatcontacts.common.view.Resource
import com.teddy.sweatcontacts.common.view.Status
import com.teddy.sweatcontacts.model.Contact

class HomeContactRepository(
    private val remoteSource: ContactRemoteSource,
    private val localSource: LocalStorageSource,
    private val paginationManager: PaginationManager
) {

    val listenResult: LiveData<Resource<List<Contact>>>
        get() = Transformations.map(remoteSource.result) { response ->
            if (response.status == Status.SUCCESS || response.status == Status.SUCCESS_MORE) {
                paginationManager.nextPage += 1
            }
        response
    }

    suspend fun getContacts() = remoteSource.getContacts()

    suspend fun getMoreContacts() = remoteSource.getMoreContacts(paginationManager.nextPage)

    fun fetchFavorites(): List<Contact> {
        return localSource.fetchFavoriteContact().map { it.toContact() }
    }
}