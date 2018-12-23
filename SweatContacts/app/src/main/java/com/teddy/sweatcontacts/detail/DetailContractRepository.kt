package com.teddy.sweatcontacts.detail

import com.teddy.sweatcontacts.common.storage.LocalStorageSource
import com.teddy.sweatcontacts.model.Contact

class DetailContractRepository(private val localSource: LocalStorageSource) {

    fun saveFavorite(contact: Contact) {
        localSource.addToFavorite(contact.toEntity())
    }

    fun removeFavorite(contact: Contact) {
        localSource.removedFromFavorite(contact.email)
    }

    fun getFavoriteStateFor(contact: Contact): Boolean {
        return localSource.getFavoriteStateFor(contact.email)
    }
}