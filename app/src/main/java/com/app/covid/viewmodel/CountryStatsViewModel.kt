package com.app.covid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.app.covid.repository.db.StatsRepository
import com.app.covid.repository.db.constant.StatType
import com.app.covid.repository.db.model.CountryStats
import com.app.covid.repository.db.model.Stat


class CountryStatsViewModel internal constructor(
    application: Application,
    private val userRepo: StatsRepository
) : AndroidViewModel(application) {

    var countryStatsLiveData: MediatorLiveData<CountryStats> = MediatorLiveData()
    var countryLiveData: MediatorLiveData<String> = MediatorLiveData()
    var statsLiveData: MediatorLiveData<List<Stat>> = MediatorLiveData()
    var showLoader: MediatorLiveData<Boolean> = MediatorLiveData()
    var countryCode = "ZA"

    init {
        addCountryStatsSource(countryCode)
        addLoader()
        addStatsSource()
    }

    private fun addLoader() {
        showLoader.postValue(true)
    }

    fun addCountryStatsSource(countryCode: String) {
        this.countryCode = countryCode
        countryStatsLiveData.addSource(userRepo.getCountryStats(this.countryCode)) {
            if (!it.data.isNullOrEmpty()) {
                countryStatsLiveData.postValue(it.data[0])
            }
        }
    }

    private fun addStatsSource(){
        statsLiveData.addSource(countryStatsLiveData){
            val statsList = mutableListOf<Stat>()

            if(!it?.totalConfirmed.isNullOrBlank()){
                val stat = Stat(StatType.CONFIRMED, it.totalConfirmed)
                statsList.add(stat)
            }
            if(!it?.totalDeaths.isNullOrBlank()){
                val stat = Stat(StatType.DEATHS, it.totalDeaths)
                statsList.add(stat)
            }
            if(!it?.totalRecovered.isNullOrBlank()){
                val stat = Stat(StatType.RECOVERED, it.totalRecovered)
                statsList.add(stat)
            }
            if(!it?.totalCritical.isNullOrBlank()){
                val stat = Stat(StatType.CRITICAL, it.totalCritical)
                statsList.add(stat)
            }
            if(!it?.country.isNullOrBlank()){
                countryLiveData.postValue(it.country)
            }
            if(!it?.dailyConfirmed.isNullOrBlank()){
                val stat = Stat(StatType.DAILY_CONFIRMED, it.dailyConfirmed)
                statsList.add(stat)
            }
            if(!it?.dailyDeaths.isNullOrBlank()){
                val stat = Stat(StatType.DAILY_DEATHS, it.dailyDeaths)
                statsList.add(stat)
            }
            if(!it?.totalDeathsPerMillionPopulation.isNullOrBlank()){
                val stat = Stat(StatType.DEATHS_PER_MILLION, it.totalDeathsPerMillionPopulation)
                statsList.add(stat)
            }
            if(!it?.totalConfirmedPerMillionPopulation.isNullOrBlank()){
                val stat = Stat(StatType.CONFIRMED_PER_MILLION, it.totalConfirmedPerMillionPopulation)
                statsList.add(stat)
            }

            statsLiveData.postValue(statsList)
            showLoader.postValue(false)
        }
    }

    fun refresh() {
        addCountryStatsSource(this.countryCode)
    }


}
