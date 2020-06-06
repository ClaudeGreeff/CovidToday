package com.app.covid.repository.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CountryStats(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") var id: Int = 0,
    @SerializedName("totalConfirmed") var totalConfirmed: String? = null,
    @SerializedName("totalDeaths") var totalDeaths: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("countryCode") var countryCode: String? = null,
    @SerializedName("totalRecovered") var totalRecovered: String? = null,
    @SerializedName("dailyConfirmed") var dailyConfirmed: String? = null,
    @SerializedName("dailyDeaths") var dailyDeaths: String? = null,
    @SerializedName("totalCritical") var totalCritical: String? = null,
    @SerializedName("totalConfirmedPerMillionPopulation") var totalConfirmedPerMillionPopulation: String? = null,
    @SerializedName("totalDeathsPerMillionPopulation") var totalDeathsPerMillionPopulation: String? = null,
    @SerializedName("lastUpdated") var lastUpdated: String? = null
)