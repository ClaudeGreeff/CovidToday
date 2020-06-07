package com.app.covid.repository.db.network

import androidx.lifecycle.LiveData
import com.app.covid.repository.db.model.CountryStats
import retrofit2.http.GET
import retrofit2.http.Query

interface StatsService {

    @GET("v3/stats/worldometer/country")
    fun getCountryStats(
        @Query("countryCode") countryCode: String
    ): LiveData<ApiResponse<List<CountryStats?>>>


}