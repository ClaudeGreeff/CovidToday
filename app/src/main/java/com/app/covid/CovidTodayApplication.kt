package com.app.covid

import androidx.lifecycle.LifecycleObserver
import androidx.multidex.MultiDexApplication
import com.app.covid.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

class CovidTodayApplication : MultiDexApplication(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            startKoin {
                object : Logger() {
                    override fun log(level: Level, msg: MESSAGE) {
                        when (level) {
                            Level.DEBUG -> Timber.d(msg)
                            Level.INFO -> Timber.i(msg)
                            Level.ERROR -> Timber.e(msg)
                        }
                    }
                }
                androidContext(this@CovidTodayApplication)
                modules(appModule)
            }

        } else {
            startKoin {
                androidContext(this@CovidTodayApplication)
                modules(appModule)
            }
        }
    }
}
