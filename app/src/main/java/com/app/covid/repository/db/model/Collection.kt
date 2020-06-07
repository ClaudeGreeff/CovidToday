package com.app.covid.repository.db.model

import com.google.gson.annotations.SerializedName

data class Collection<T>(
    @SerializedName("data", alternate = ["collection"]) var data: List<T> = listOf(),
    @SerializedName("meta") var meta: Meta,
    @SerializedName("data_limited") var dataLimited: Boolean = false
)
