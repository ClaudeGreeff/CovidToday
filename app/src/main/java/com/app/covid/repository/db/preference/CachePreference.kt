package com.app.covid.repository.db.preference

import android.content.Context
import android.content.SharedPreferences

object CachePreference {
    private const val SHARED_PREFERENCE_NAME = "cache_preference"

    private fun getSharedPreferences(context: Context)
            : SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
}