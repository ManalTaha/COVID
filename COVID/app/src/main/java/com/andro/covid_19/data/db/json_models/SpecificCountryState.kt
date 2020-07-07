package com.andro.retro.json_models

data class SpecificCountryState(
    val country: String,
    val latest_stat_by_country: List<LatestStatByCountry>
)