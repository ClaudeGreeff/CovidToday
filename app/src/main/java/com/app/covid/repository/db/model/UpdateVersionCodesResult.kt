package com.app.covid.repository.db.model

import com.google.gson.annotations.SerializedName

data class UpdateVersionCodesResult(
    @SerializedName("hard_build") var hardUpdateVersionCode: Int? = null,
    @SerializedName("soft_build") var softUpdateVersionCode: Int? = null
)
