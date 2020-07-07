package com.andro.covid_19

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.andro.covid_19.ui.settings.SettingsViewModel


class GlobalApplication: Application() {

companion object{
    private var instance: GlobalApplication? = null
    fun getApplicationContext() : Context {
        return instance!!.applicationContext
    }
}
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        SettingsViewModel.context = applicationContext
    }
    init {
        instance = this
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                applicationContext.getString(R.string.channel_id),
                applicationContext.getString(R.string.channel_id),
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel1)
        }
    }
}

