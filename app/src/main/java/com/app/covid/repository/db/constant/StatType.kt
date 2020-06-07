package com.app.covid.repository.db.constant

enum class StatType(val value: String) {
    CONFIRMED("totalConfirmed"),
    DEATHS("totalDeaths"),
    RECOVERED("totalRecovered"),
    DAILY_CONFIRMED("dailyConfirmed"),
    DAILY_DEATHS("dailyDeaths"),
    CRITICAL("totalCritical"),
    CONFIRMED_PER_MILLION("totalConfirmedPerMillionPopulation"),
    DEATHS_PER_MILLION("totalDeathsPerMillionPopulation")
}