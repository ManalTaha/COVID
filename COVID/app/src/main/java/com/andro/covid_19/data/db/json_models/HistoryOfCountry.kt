package com.andro.retro.json_models

data class HistoryOfCountry(
    val country: String,
    val stat_by_country: List<StatByCountry>
)