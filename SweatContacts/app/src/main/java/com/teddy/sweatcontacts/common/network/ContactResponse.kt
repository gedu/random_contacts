package com.teddy.sweatcontacts.common.network

import com.teddy.sweatcontacts.model.Contact
import com.teddy.sweatcontacts.model.ContactName

class ResultContactResponse(val results: List<ContactResponse>)

class ContactResponse(
    private val gender: String,
    private val name: ContactName,
    private val email: String,
    private val phone: String,
    private val picture: ContactName
) {
    fun toContact(): Contact {
        return Contact(gender, name, email, phone, picture)
    }
}