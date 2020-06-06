package com.app.covid.repository.db.model

import com.google.gson.annotations.SerializedName

data class UpdateVersionCodesResultHolder(
        @SerializedName("application_version") var applicationVersion: UpdateVersionCodesResult? = null
)
