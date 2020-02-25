package com.arcchitecturepatterns.common.domain.entities.saved

import android.os.Parcel
import android.os.Parcelable

data class SavedAtDomainModel(
    val savedAt: Long,
    val id: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeLong(savedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SavedAtDomainModel> {
        override fun createFromParcel(parcel: Parcel): SavedAtDomainModel {
            return SavedAtDomainModel(parcel)
        }

        override fun newArray(size: Int): Array<SavedAtDomainModel?> {
            return arrayOfNulls(size)
        }
    }

}