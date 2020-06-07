package com.app.covid.di

import androidx.room.Room
import com.app.covid.BuildConfig
import com.app.covid.core.AppExecutors
import com.app.covid.repository.db.MainDataBase
import com.app.covid.repository.db.StatsRepository
import com.app.covid.repository.db.StatsRepositoryImpl
import com.app.covid.repository.db.network.ServiceGenerator
import com.app.covid.repository.db.network.StatsService
import com.app.covid.viewmodel.CountryStatsViewModel
import com.example.fastestresponse.constants.DbFile
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val appModule = module {

    // Repositories
    single { AppExecutors.createInstance() }
    single<StatsRepository> { StatsRepositoryImpl(get(), get(), get(), get()) }

    // Network Services for Latch - Auth required
    single {
        ServiceGenerator(
            "https://api.coronatracker.com/"
        )
    }

    // Database
    single {
        Room.databaseBuilder(
            get(),
            MainDataBase::class.java,
            DbFile.CORE_DB_FILE.fileName
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { (get() as ServiceGenerator).createService(StatsService::class.java) }

    // View models
    viewModel { CountryStatsViewModel(get(), get()) }
}

