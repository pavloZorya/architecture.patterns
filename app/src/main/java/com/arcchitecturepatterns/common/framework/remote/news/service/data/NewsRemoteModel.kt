package com.arcchitecturepatterns.common.framework.remote.news.service.data

import android.os.Parcel
import android.os.Parcelable
import com.arcchitecturepatterns.common.domain.entities.news.NewsDomainModel
import com.google.gson.annotations.SerializedName

class NewsRemoteModel(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("thumbnail")
    val imageLink: String = "https://startup.network/upload/iblock/b08/reddit_revendique_plus_de_330_millions_d_utilisateurs_a_travers_le_monde.jpg",
    @SerializedName("url")
    val pageLink: String = "https://www.reddit.com/",
    @SerializedName("score")
    val score: Long
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(imageLink)
        parcel.writeString(pageLink)
        parcel.writeLong(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsRemoteModel> {
        override fun createFromParcel(parcel: Parcel): NewsRemoteModel {
            return NewsRemoteModel(
                parcel
            )
        }

        override fun newArray(size: Int): Array<NewsRemoteModel?> {
            return arrayOfNulls(size)
        }
    }
}

fun NewsRemoteModel.toDomainModel(): NewsDomainModel {
    return NewsDomainModel(title, pageLink, imageLink)
}

fun List<NewsRemoteModel>.toDomainModel(): List<NewsDomainModel> {
    return map { it.toDomainModel() }
}