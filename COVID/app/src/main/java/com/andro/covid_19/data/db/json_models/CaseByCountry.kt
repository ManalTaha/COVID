package com.andro.retro.json_models

data class CaseByCountry(
    val countries_stat: List<CountriesStat>,
    val statistic_taken_at: String
)