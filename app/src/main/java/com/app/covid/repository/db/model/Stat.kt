package com.app.covid.repository.db.model

import com.app.covid.repository.db.constant.StatType
import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("reference") var reference: StatType? = null,
    @SerializedName("count") var count: String? = null
)