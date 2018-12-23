package com.teddy.sweatcontacts.common.entity

import com.teddy.sweatcontacts.model.Contact
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ContactEntity(
    var gender: String = "",
    var name: ContactNameEntity? = null,
    @PrimaryKey var email: String = "",
    var phone: String = "",
    var picture: ContactPictureEntity? = null,
    var age: String = "",
    var favorite: Boolean = false
) : RealmObject() {

    fun toContact() = Contact(gender, name!!.toContactName(), email, phone, picture!!.toContactPicture(), age, favorite)
}