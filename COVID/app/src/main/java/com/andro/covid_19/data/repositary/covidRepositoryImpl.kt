package com.andro.covid_19.data.repositary

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.andro.covid_19.data.api_services.ApiHandler
import com.andro.covid_19.data.db.dao.CountriesStatDao
import com.andro.covid_19.data.db.dao.CountrystatDao
import com.andro.covid_19.data.db.CovidDataBase
import com.andro.covid_19.data.db.dao.WorldTotalStatesDao
import com.andro.retro.json_models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class covidRepositoryImpl(context: Context, private var apiHandler: ApiHandler) : covidRepository,
    CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private lateinit var countrystatDao: CountrystatDao
    private lateinit var countriesStatDao: CountriesStatDao
    private lateinit var worldTotalStatesDao: WorldTotalStatesDao
//obj dao

    init {
        val database: CovidDataBase? = CovidDataBase.getInstance(context)
        if (database != null) {
            countrystatDao = database.CountrystatDao()!!
            countriesStatDao = database.CountriesStatDao()!!
            worldTotalStatesDao = database.WorldTotalStatesDao()!!

        }
        apiHandler.apply {
                        allCountriesCases.observeForever { newAllCountriesState ->
                val countries_stat = newAllCountriesState.countries_stat
                for (i in countries_stat.indices) {
                    if(newAllCountriesState.countries_stat[i].country_name != ""){
                        saveCountriesStat(newAllCountriesState.countries_stat[i]) }
                }
                    }


            worldState.observeForever {
                addWorldTotalStates(it)
            }
            allAffectedCountries.observeForever {  }
            countryHistoryInDate.observeForever {  }
        }

    }

    // get all countries states
    override fun getAllCountriesState(): LiveData<List<CountriesStat>> {
        launch {
            apiHandler.getCaseByCountry()
        }
        return countriesStatDao.getAll()
    }


    override fun saveCountriesStat(countriesStat: CountriesStat) {
        launch {
            InsertCountries(countriesStat)
        }
    }

    private suspend fun InsertCountries(countriesStat: CountriesStat) {
        withContext(Dispatchers.IO)
        {
            countriesStatDao.insert(countriesStat)
        }
    }

    //get world states
    override fun getWorldTotalStates(): LiveData<List<WorldTotalStates>> {
        launch {
            apiHandler.getWorldTotalState()
        }
        return worldTotalStatesDao.getAll()
    }

    override fun addWorldTotalStates(WorldTotalStates: WorldTotalStates) {
        launch {
            insertWorldTotalStates(WorldTotalStates)
        }
    }

    private suspend fun insertWorldTotalStates(worldTotalStates: WorldTotalStates) {
        withContext(Dispatchers.IO)
        {
            worldTotalStatesDao.insert(worldTotalStates)
        }
    }


// get affectedCountry
    override fun getAffectedCountries(): LiveData<AllAffectedCountries> {
        launch {  apiHandler.getAffectedCountries() }
        return apiHandler.allAffectedCountries
    }

    // get History for country
    override fun getHistoryForCountry(countryName: String, date: String): LiveData<HistoryOfCountry> {
    launch {  apiHandler.getHistoryForCountryInDate(countryName , date) }
        return apiHandler.countryHistoryInDate
    }

    override fun getRandomPicture(): LiveData<Bitmap> {
        launch {  apiHandler.getRandomPicture() }
        return apiHandler.randomImage
    }



}