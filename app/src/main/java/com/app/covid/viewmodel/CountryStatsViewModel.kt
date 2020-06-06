package com.app.covid.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.app.covid.repository.db.StatsRepository
import com.app.covid.repository.db.model.CountryStats
import timber.log.Timber


class CountryStatsViewModel internal constructor(
    application: Application,
    private val userRepo: StatsRepository
) : AndroidViewModel(application) {

    var countryStatsLiveData : MediatorLiveData<CountryStats> = MediatorLiveData()

    init {
        countryStatsLiveData.addSource(userRepo.getCountryStats("ZA")){
            if(!it.data.isNullOrEmpty()){
                countryStatsLiveData.postValue(it.data[0])
            }
        }
    }

    
}
