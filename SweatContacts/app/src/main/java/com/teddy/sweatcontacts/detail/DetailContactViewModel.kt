package com.teddy.sweatcontacts.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.teddy.sweatcontacts.common.base.BaseViewModel
import com.teddy.sweatcontacts.model.Contact
import kotlin.coroutines.CoroutineContext

class DetailContactViewModel(
    private val repository: DetailContractRepository,
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
            if (favorite) repository.saveFavorite(this) else repository.removeFavorite(this)
            _currentContact.value = this
        }
    }

    private fun getFavoriteAfterUpdate(contact: Contact) = contact.favorite
}