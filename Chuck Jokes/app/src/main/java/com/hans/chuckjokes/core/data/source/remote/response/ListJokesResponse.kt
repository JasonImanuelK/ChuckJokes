package com.hans.chuckjokes.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListJokesResponse(
    @field:SerializedName("result")
    val result: List<JokesResponse>
)