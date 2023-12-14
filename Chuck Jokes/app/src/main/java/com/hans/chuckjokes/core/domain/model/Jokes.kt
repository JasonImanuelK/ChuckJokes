package com.hans.chuckjokes.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Jokes(
    var icon_url: String,
    var id: String,
    var url: String,
    var value: String,
    var isFavorite: Boolean
) : Parcelable {

// Primary constructor
constructor(icon_url: String, id: String, url: String, value: String) : this(icon_url, id, url, value, isFavorite = false)
}