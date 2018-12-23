package com.teddy.sweatcontacts.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.teddy.sweatcontacts.common.base.BaseViewModel
import com.teddy.sweatcontacts.common.base.FavoriteManager
import com.teddy.sweatcontacts.model.Contact
import kotlin.coroutines.CoroutineContext

class DetailContactViewModel(
    private val repository: DetailContractRepository,
    private val favoriteManager: FavoriteManager,
    mainDispatcher: CoroutineContext
) : BaseViewModel(mainDispatcher) {


    private val _currentContact = MutableLiveData<Contact>()
    val currentContact
        get() = _currentContact as LiveData<Contact>

    val favoriteState: LiveData<Boolean> = Transformations.map(currentContact, ::getFavoriteAfterUpdate)

    fun addContact(contact: Contact) {
        contact.favorite = repository.getFavoriteStateFor(contact)
        _currentContact.value = contact
    }

    fun updateFavoriteState() {
        _currentContact.value?.apply {
            favorite = !this.favorite
            favoriteManager.updateFavorite(this)
            _currentContact.value = this
        }
    }

    private fun getFavoriteAfterUpdate(contact: Contact) = contact.favorite
}