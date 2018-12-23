package com.teddy.sweatcontacts.model

import android.os.Parcelable
import com.teddy.sweatcontacts.R
import com.teddy.sweatcontacts.common.entity.ContactEntity
import com.teddy.sweatcontacts.common.entity.ContactNameEntity
import com.teddy.sweatcontacts.common.entity.ContactPictureEntity
import kotlinx.android.parcel.Parcelize

const val FEMALE = "female"
const val MALE = "male"

@Parcelize
data class Contact(
    val gender: String,
    val name: ContactName,
    val email: String,
    val phone: String,
    val picture: ContactPicture,
    val age: String,
    var favorite: Boolean = false
) : Parcelable {

    val fullName get() = "${name.first} ${name.last}"

    fun getGenderRes() = if (gender == FEMALE) R.drawable.ic_female_gender else R.drawable.ic_male_gender

    fun toEntity(): ContactEntity {
        return ContactEntity(
            gender,
            ContactNameEntity(email, name.title, name.first, name.last),
            email,
            phone,
            ContactPictureEntity(email, picture.large, picture.medium, picture.thumbnail),
            age,
            favorite
        )
    }
}

@Parcelize
class ContactName(
    val title: String,
    val first: String,
    val last: String
) : Parcelable

@Parcelize
class ContactPicture(
    val large: String,
    val medium: String,
    val thumbnail: String
) : Parcelable

@Parcelize
class ContactBirth(val age: String) : Parcelable