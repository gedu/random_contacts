package com.teddy.sweatcontacts.model

class Contact(
    val gender: String,
    val name: ContactName,
    val email: String,
    val phone: String,
    val picture: ContactName
)

class ContactName(
    val title: String,
    val first: String,
    val last: String
)

class ContactPicture(
    val large: String,
    val medium: String,
    val thumbnail: String
)