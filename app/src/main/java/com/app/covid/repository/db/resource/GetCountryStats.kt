package com.app.covid.repository.db.resource

import androidx.lifecycle.LiveData
import com.app.covid.core.AppExecutors
import com.app.covid.repository.db.model.CountryStats
import com.app.covid.repository.db.network.ApiResponse
import com.app.covid.repository.db.network.StatsService
import timber.log.Timber

class GetCountryStats(appExecutors: AppExecutors,
                                    var countryCode: String,
                                    service: StatsService
) {

    private var networkResource: NetworkResource<List<CountryStats?>> =
            object : NetworkResource<List<CountryStats?>>(appExecutors) {

                override fun saveCallResult(item: List<CountryStats?>?) {
                    Timber.i("Successfully pulled country stats")
                }

                override fun createCall(): LiveData<ApiResponse<List<CountryStats?>>> {
                     return service.getCountryStats(countryCode)
                }

            }

    fun getResult(): LiveData<Resource<List<CountryStats?>>> {
        return networkResource.asLiveData()
    }
}