package com.arcchitecturepatterns.common.domain.entities.news

import android.os.Parcel
import android.os.Parcelable

//@Parcelize
data class NewsDomainModel(
    val title: String? = "",
    val description: String? = "",
    val link: String? = "",
    var savedAt: Int = -1,
    val id: Int = 0
) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(link)
        parcel.writeInt(savedAt)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsDomainModel> {
        override fun createFromParcel(parcel: Parcel): NewsDomainModel {
            return NewsDomainModel(parcel)
        }

        override fun newArray(size: Int): Array<NewsDomainModel?> {
            return arrayOfNulls(size)
        }
    }

}