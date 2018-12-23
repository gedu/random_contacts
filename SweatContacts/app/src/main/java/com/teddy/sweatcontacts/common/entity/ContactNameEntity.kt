package com.teddy.sweatcontacts.common.entity

import com.teddy.sweatcontacts.model.ContactName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ContactNameEntity(
    @PrimaryKey var email: String = "",
    var title: String = "",
    var first: String = "",
    var last: String = ""
) : RealmObject() {

    fun toContactName() = ContactName(title, first, last)
}