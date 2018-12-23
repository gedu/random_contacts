package com.teddy.sweatcontacts.home

import com.teddy.sweatcontacts.common.network.ContactRemoteSource
import com.teddy.sweatcontacts.common.storage.LocalStorageSource
import com.teddy.sweatcontacts.model.Contact

class HomeContactRepository(
    private val remoteSource: ContactRemoteSource,
    private val localSource: LocalStorageSource
) {

    val listenResult get() = remoteSource.result

    suspend fun getContacts() = remoteSource.getContacts()

    fun fetchFavorites(): List<Contact> {
        return localSource.fetchFavoriteContact().map { it.toContact() }
    }
}