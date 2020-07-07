package com.andro.retro.json_models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WorldTotalStates")
data class WorldTotalStates(
    val new_cases: String?,
    val new_deaths: String?,
    @PrimaryKey
    val statistic_taken_at: String,
    val total_cases: String,
    val total_deaths: String?,
    val total_recovered: String?
)