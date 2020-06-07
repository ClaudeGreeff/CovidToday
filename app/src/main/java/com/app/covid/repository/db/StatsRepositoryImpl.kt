package com.app.covid.repository.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.app.covid.core.AppExecutors
import com.app.covid.repository.db.model.CountryStats
import com.app.covid.repository.db.network.ApiResponse
import com.app.covid.repository.db.network.StatsService
import com.app.covid.repository.db.resource.GetCountryStats
import com.app.covid.repository.db.resource.Resource

class StatsRepositoryImpl(
    val context: Context,
    private val appExecutors: AppExecutors,
    private val service: StatsService,
    private val db: MainDataBase
) : StatsRepository {

    //CountryStats
    private var getCountryStats: GetCountryStats? = null

    override fun getCountryStats(countryCode: String): LiveData<Resource<List<CountryStats?>>> {
        getCountryStats = GetCountryStats(
            appExecutors,
            countryCode,
            service
        )
        return getCountryStats!!.getResult()
    }
}