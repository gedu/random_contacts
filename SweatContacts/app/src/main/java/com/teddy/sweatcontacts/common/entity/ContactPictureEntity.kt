package com.teddy.sweatcontacts.common.entity

import com.teddy.sweatcontacts.model.ContactPicture
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ContactPictureEntity(
    @PrimaryKey var email: String = "",
    var large: String = "",
    var medium: String = "",
    var thumbnail: String = ""
) : RealmObject() {
    fun toContactPicture() = ContactPicture(large, medium, thumbnail)
}