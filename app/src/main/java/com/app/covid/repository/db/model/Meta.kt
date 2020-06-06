package com.app.covid.repository.db.model

import com.google.gson.annotations.SerializedName

data class Meta(
        @SerializedName("page") var page: Int = 0,
        @SerializedName("per_page") var perPage: Int = 0,
        @SerializedName("total_pages") var totalPages: Int = 0,
        @SerializedName("total_entries") var totalEntries: Int = 0
)
