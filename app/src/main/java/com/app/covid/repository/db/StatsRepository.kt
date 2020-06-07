package com.app.covid.repository.db

import androidx.lifecycle.LiveData
import com.app.covid.repository.db.model.CountryStats
import com.app.covid.repository.db.network.ApiResponse
import com.app.covid.repository.db.resource.Resource

interface StatsRepository {
    fun getCountryStats(countryCode: String): LiveData<Resource<List<CountryStats?>>>
}