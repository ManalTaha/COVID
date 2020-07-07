package com.andro.covid_19

import android.content.SharedPreferences
import com.andro.retro.json_models.LatestStatByCountry

object SavedPreferences {

    fun saveCountry(countryNo: Int){
        val settings: SharedPreferences = GlobalApplication.getApplicationContext().getSharedPreferences(GlobalApplication.getApplicationContext().getString(R.string.country_data),0)
        val editor = settings.edit()
        editor.putInt(GlobalApplication.getApplicationContext().getString(R.string.country_name), countryNo)
        editor.commit()
    }

    fun getCountry(): Int? {
        val settings: SharedPreferences = GlobalApplication.getApplicationContext().getSharedPreferences(GlobalApplication.getApplicationContext().getString(R.string.country_data), 0)
         return  settings.getInt(GlobalApplication.getApplicationContext().getString(R.string.country_name),-1)

    }

    fun resetCountry() {
        saveCountry(-1)
    }

    fun saveInterval(intervalNo: Int){
        val settings: SharedPreferences = GlobalApplication.getApplicationContext().getSharedPreferences(GlobalApplication.getApplicationContext().getString(R.string.country_data),0)
        val editor = settings.edit()
        editor.putInt(GlobalApplication.getApplicationContext().getString(R.string.interval_number), intervalNo)
        editor.commit()
    }

    fun getInterval(): Int? {
        val settings: SharedPreferences = GlobalApplication.getApplicationContext().getSharedPreferences(GlobalApplication.getApplicationContext().getString(R.string.country_data), 0)
        return  settings.getInt(GlobalApplication.getApplicationContext().getString(R.string.interval_number),0)

    }

    fun resetInterval() {
        saveInterval(0)
    }

}