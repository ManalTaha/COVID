package com.andro.covid_19.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andro.retro.json_models.StatByCountry
import com.andro.retro.json_models.WorldTotalStates

@Dao
interface WorldTotalStatesDao {
    @Query("SELECT * FROM WorldTotalStates")
    fun getAll(): LiveData<List<WorldTotalStates>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(WorldTotalStates: WorldTotalStates?)
}