package com.teddy.sweatcontacts.model

import android.os.Parcelable
import com.teddy.sweatcontacts.R
import kotlinx.android.parcel.Parcelize

const val FEMALE = "female"
const val MALE = "male"

@Parcelize
data class Contact(
    val gender: String,
    val name: ContactName,
    val email: String,
    val phone: String,
    val picture: ContactPicture
) : Parcelable {

    val fullName get() = "${name.first} ${name.last}"

    fun getGenderRes() = if (gender == FEMALE) R.drawable.ic_female_gender else R.drawable.ic_male_gender
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