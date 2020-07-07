package com.andro.covid_19

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*


object AlarmManagerHandler {
    fun setAlarmManager(country: String, countryNo: Int, interval: String, intervalNo: Int) {
        cancelAlarm(country)
        val alarmManager =
            GlobalApplication.getApplicationContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent =
            Intent(GlobalApplication.getApplicationContext(), NotificationReciever::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(
            GlobalApplication.getApplicationContext().getString(R.string.country_name),
            country
        )
        val pendingIntent = PendingIntent.getBroadcast(
            GlobalApplication.getApplicationContext(),
            country.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val cal1: Calendar = Calendar.getInstance()

        when (interval) {
            GlobalApplication.getApplicationContext().getString(R.string.one_hour) -> {
                cal1.add(Calendar.HOUR, 1)
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    cal1.timeInMillis,
                    AlarmManager.INTERVAL_HOUR,
                    pendingIntent

                )
            }
            GlobalApplication.getApplicationContext().getString(R.string.two_hours) -> {
                cal1.add(Calendar.HOUR, 2)   // for test comment this
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    cal1.timeInMillis,
                    AlarmManager.INTERVAL_HOUR * 2,    //for teest 2 * 60 * 1000 2 min
                    pendingIntent
                )
            }

            GlobalApplication.getApplicationContext().getString(R.string.five_hours) -> {
                cal1.add(Calendar.HOUR, 5)
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    cal1.timeInMillis,
                    AlarmManager.INTERVAL_HOUR * 5,
                    pendingIntent
                )
            }
            GlobalApplication.getApplicationContext().getString(R.string.once_day) -> {
                cal1.add(Calendar.HOUR, 24)
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    cal1.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent

                )
            }
        }
        SavedPreferences.saveCountry(countryNo)
        SavedPreferences.saveInterval(intervalNo)
    }

    fun cancelAlarm(country: String) {
        SavedPreferences.resetCountry()
        SavedPreferences.resetInterval()
        val alarmManager =
            GlobalApplication.getApplicationContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent =
            Intent(GlobalApplication.getApplicationContext(), NotificationReciever::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(
            GlobalApplication.getApplicationContext().getString(R.string.country_name),
            country
        )
        val pendingIntent = PendingIntent.getBroadcast(
            GlobalApplication.getApplicationContext(),
            country.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
    }
}

