package com.andro.retro.json_models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CountryState")
data class StatByCountry(
    val active_cases: String?,
    val country_name: String?,
     @PrimaryKey
    val id: String,
    val new_cases: String?,
    val new_deaths: String?,
    val record_date: String?,
    val region:String?,
    val serious_critical: String?,
    val total_cases: String?,
    val total_cases_per1m: String?,
    val total_deaths: String?,
    val total_recovered: String?
)