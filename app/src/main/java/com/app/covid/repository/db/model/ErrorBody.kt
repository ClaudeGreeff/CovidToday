package com.app.covid.repository.db.model

import com.google.gson.annotations.SerializedName

data class ErrorBody(
        @SerializedName("title") var title: String? = null,
        @SerializedName("message") var message: String? = null,
        @SerializedName("description") var description: String? = null,
        @SerializedName("type") var type: String? = null
)
