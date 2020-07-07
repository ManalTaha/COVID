package com.andro.covid_19.ui.home

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andro.covid_19.data.api_services.ApiHandler
import com.andro.covid_19.data.api_services.ApiInterface
import com.andro.covid_19.data.network.ConnectivityInterceptorImpl
import com.andro.covid_19.data.repositary.covidRepository
import com.andro.covid_19.data.repositary.covidRepositoryImpl
import com.andro.retro.json_models.CountriesStat
import com.andro.retro.json_models.StatByCountry
import com.andro.retro.json_models.WorldTotalStates

class HomeViewModel() : ViewModel() {

    companion object {
        lateinit var context:Context
    }
    private val api = ApiInterface(ConnectivityInterceptorImpl(context))
    private val apiHandler = ApiHandler(api)

    private var repository: covidRepository =covidRepositoryImpl(context,apiHandler)


    fun getCountriesData(): LiveData<List<CountriesStat>> = repository.getAllCountriesState()
    fun getWorldTotalStates(): LiveData<List<WorldTotalStates>> = repository.getWorldTotalStates()

}