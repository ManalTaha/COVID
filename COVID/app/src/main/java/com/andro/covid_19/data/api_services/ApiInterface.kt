package com.andro.covid_19.data.api_services

import com.andro.covid_19.data.network.ConnectivityInterceptor
import com.andro.retro.json_models.*
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface ApiInterface {
    @Headers(
        "Content-Type: application/json",
        "x-rapidapi-host: coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 423421ec09mshbbc05ab5ac1e0dbp1dabddjsn196db168c54a")
    @GET("affected.php")
    fun getAffectedCountriesAsync(): Deferred<AllAffectedCountries>

    @Headers(
        "Content-Type: application/json",
        "x-rapidapi-host: coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 423421ec09mshbbc05ab5ac1e0dbp1dabddjsn196db168c54a")
    @GET("cases_by_country.php")
    fun getCaseByCountryAsync(): Deferred<CaseByCountry>

    @Headers(
        "Content-Type: application/json",
        "x-rapidapi-host: coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 423421ec09mshbbc05ab5ac1e0dbp1dabddjsn196db168c54a")
    @GET("random_masks_usage_instructions.php")
    fun getRandomInstructionAsync(): Deferred<ResponseBody>

    @Headers(
        "Content-Type: application/json",
        "x-rapidapi-host: coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 423421ec09mshbbc05ab5ac1e0dbp1dabddjsn196db168c54a")
    @GET("latest_stat_by_country.php")
    fun getSpecificCountryStateAsync(@Query("country") countryName: String): Deferred<SpecificCountryState>

    @Headers(
        "Content-Type: application/json",
        "x-rapidapi-host: coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 423421ec09mshbbc05ab5ac1e0dbp1dabddjsn196db168c54a")
    @GET("worldstat.php")
    fun getWorldTotalStateAsync(): Deferred<WorldTotalStates>

    @Headers(
        "Content-Type: application/json",
        "x-rapidapi-host: coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key: 423421ec09mshbbc05ab5ac1e0dbp1dabddjsn196db168c54a")
    @GET("history_by_particular_country_by_date.php")
    fun getHistoryForCountryInDateAsync(@Query("country") countryName: String,
                                        @Query("date") date: String): Deferred<HistoryOfCountry>


    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApiInterface {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }


            val okHttpClient = OkHttpClient.Builder().apply {
                readTimeout(100, TimeUnit.SECONDS)
                connectTimeout(100, TimeUnit.SECONDS)
                    addInterceptor(requestInterceptor)
                    addInterceptor(connectivityInterceptor)
            }


                .build()

            val gsonBuilder = GsonBuilder()
            gsonBuilder.setLenient()
            val gson = gsonBuilder.create()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://coronavirus-monitor.p.rapidapi.com/coronavirus/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiInterface::class.java)
        }
    }

}