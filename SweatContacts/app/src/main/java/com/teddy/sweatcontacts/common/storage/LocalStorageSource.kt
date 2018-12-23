package com.teddy.sweatcontacts.common.storage

import com.teddy.sweatcontacts.common.entity.ContactEntity
import io.realm.Realm
import io.realm.kotlin.where

private const val FAVORITE_COLUMN = "favorite"
private const val EMAIL_COLUMN = "email"

class LocalStorageSource {

    fun addToFavorite(contactEntity: ContactEntity) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { it.copyToRealmOrUpdate(contactEntity) }
        }
    }

    fun fetchFavoriteContact(): List<ContactEntity> {
        return Realm.getDefaultInstance().use {
            val contacts = it.where<ContactEntity>().equalTo(FAVORITE_COLUMN, true).findAll()
            it.copyFromRealm(contacts)
        }
    }

    fun removedFromFavorite(email: String) {
        Realm.getDefaultInstance().use {
            val contactToRemove = it.where<ContactEntity>().equalTo(EMAIL_COLUMN, email).findFirst()
            it.executeTransaction { contactToRemove?.deleteFromRealm() }
        }
    }

    fun getFavoriteStateFor(contactEmail: String): Boolean {
        return Realm.getDefaultInstance().use {
            val contact = it.where<ContactEntity>().equalTo(EMAIL_COLUMN, contactEmail).findFirst()
            contact?.favorite ?: false
        }
    }
}