package com.andro.covid_19.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.andro.retro.json_models.StatByCountry

@Dao
interface CountrystatDao {
    @Query("SELECT * FROM CountryState")
    fun getAll(): LiveData<List<StatByCountry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(StatByCountry: StatByCountry?)



}