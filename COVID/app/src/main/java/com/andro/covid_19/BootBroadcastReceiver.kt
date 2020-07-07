package com.andro.covid_19

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.andro.covid_19.ui.settings.SettingsViewModel

class BootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        print("hellppppp")
        val settingsViewModel = SettingsViewModel()
        if (p0 != null) {
            SettingsViewModel.context = p0
        }
        if (p1?.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            val countryNum = SavedPreferences.getCountry()
            val intervalNum = SavedPreferences.getInterval()
            if (countryNum != null && countryNum != -1 && intervalNum != null && intervalNum != 0) {
                var interval = ""
                when (intervalNum) {
                    1 ->
                        interval =
                            GlobalApplication.getApplicationContext().getString(R.string.one_hour)
                    2 -> interval =
                        GlobalApplication.getApplicationContext().getString(R.string.two_hours)
                    3 -> interval =
                        GlobalApplication.getApplicationContext().getString(R.string.five_hours)
                    4 -> interval =
                        GlobalApplication.getApplicationContext().getString(R.string.once_day)

                }
                val countries: ArrayList<String> = ArrayList()
                settingsViewModel.getAllAffectedCountries().observeForever{
                    for (i in 0 until it.affected_countries.size - 1) {
                        if (it.affected_countries[i] != "") {
                            countries.add(it.affected_countries[i])
                        }
                    }
                }

                AlarmManagerHandler.setAlarmManager(countries[countryNum], countryNum, interval, intervalNum)
            }
        }
    }
}

