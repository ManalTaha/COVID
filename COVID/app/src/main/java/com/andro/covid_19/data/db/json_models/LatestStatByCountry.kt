package com.andro.retro.json_models


data class LatestStatByCountry(
    val active_cases: String,
    val country_name: String,
    val id: String,
    val new_cases: String,
    val new_deaths: String,
    val record_date: String,
    val region: Any,
    val serious_critical: String,
    val total_cases: String,
    val total_cases_per1m: String,
    val total_deaths: String,
    val total_recovered: String
)