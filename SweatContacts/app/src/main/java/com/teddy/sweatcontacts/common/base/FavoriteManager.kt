package com.teddy.sweatcontacts.common.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.teddy.sweatcontacts.common.storage.LocalStorageSource
import com.teddy.sweatcontacts.model.Contact

class FavoriteManager(private val localSource: LocalStorageSource) {

    private val _favorites = MutableLiveData<List<Contact>>()
    val favorites
        get() = _favorites as LiveData<List<Contact>>

    fun fetchFavorites() {
        _favorites.value = localSource.fetchFavoriteContact().map { it.toContact() }
    }

    fun updateFavorite(contact: Contact) {
        if (contact.favorite) {
            _favorites.value = localSource.addToFavorite(contact.toEntity()).map { it.toContact() }
        } else {
            _favorites.value = localSource.removedFromFavorite(contact.email).map { it.toContact() }
        }
    }
}