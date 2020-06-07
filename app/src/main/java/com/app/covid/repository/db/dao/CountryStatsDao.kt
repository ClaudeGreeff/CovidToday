package com.app.covid.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.app.covid.repository.db.model.CountryStats

@Dao
interface CountryStatsDao {

    @Query("select * from CountryStats")
    fun getCountryStats(): LiveData<List<CountryStats>>
}