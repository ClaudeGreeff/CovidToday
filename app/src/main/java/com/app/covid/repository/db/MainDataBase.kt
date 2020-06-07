package com.app.covid.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.covid.repository.db.dao.CountryStatsDao
import com.app.covid.repository.db.model.CountryStats

@Database(
    entities = [
        CountryStats::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MainDataBase : RoomDatabase() {
    abstract fun countryStatsDao(): CountryStatsDao
}