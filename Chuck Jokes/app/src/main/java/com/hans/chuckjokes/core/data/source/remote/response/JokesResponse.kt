package com.hans.chuckjokes.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class JokesResponse(
    @field:SerializedName("icon_url")
    val icon_url: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("value")
    val value: String
)