package com.andro.covid_19.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.andro.retro.json_models.CountriesStat
@Dao
interface CountriesStatDao {
    @Query("SELECT * FROM CountriesStat ORDER BY country_name ASC ")
    fun getAll(): LiveData<List<CountriesStat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(CountriesStat: CountriesStat?)


}