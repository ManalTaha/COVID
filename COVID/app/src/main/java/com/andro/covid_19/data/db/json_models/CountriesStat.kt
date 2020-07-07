package com.andro.retro.json_models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "CountriesStat")
data class CountriesStat (
    val active_cases: String?,
    val cases: String?,
    @PrimaryKey
    val country_name: String,
    val deaths: String?,
    val new_cases: String?,
    val new_deaths: String?,
    val region: String?,
    val serious_critical: String?,
    val total_cases_per_1m_population: String?,
    val total_recovered: String?
): Serializable