package com.andro.covid_19.data.repositary

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.andro.retro.json_models.*

interface covidRepository {
    //want it to be asynchronous // enable us to call this function from a call routine

    fun getAllCountriesState(): LiveData<List<CountriesStat>>
    fun saveCountriesStat(CountriesStat: CountriesStat)

    fun getWorldTotalStates(): LiveData<List<WorldTotalStates>>
    fun addWorldTotalStates(WorldTotalStates:WorldTotalStates)

    fun getHistoryForCountry(countryName: String, date: String): LiveData<HistoryOfCountry>
    fun getAffectedCountries(): LiveData<AllAffectedCountries>
    fun getRandomPicture():LiveData<Bitmap>



}