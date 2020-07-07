package com.andro.covid_19

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.andro.covid_19.data.api_services.ApiHandler
import com.andro.covid_19.data.api_services.ApiInterface
import com.andro.covid_19.data.network.ConnectivityInterceptorImpl
import com.andro.covid_19.ui.settings.SettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotificationReciever: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val api = ApiInterface(ConnectivityInterceptorImpl(SettingsViewModel.context))
        val apiHandler = ApiHandler(api)
        val countryName =
            p1?.getStringExtra(GlobalApplication.getApplicationContext().getString(R.string.country_name))

            GlobalScope.launch(Dispatchers.Main) {
                if (countryName != null) {
                    apiHandler.getSpecificCountryState(countryName)
                }
                apiHandler.specificCountryState.observeForever {
                    val update: String =
                        "Total: ${it.latest_stat_by_country[0].total_cases} Recovered: ${it.latest_stat_by_country[0].total_recovered} Deaths: ${it.latest_stat_by_country[0].total_deaths}  "
                    countryName?.let { it1 -> createNotification(it1, update) }
                }

            }

        }

    }


    fun createNotification(country: String, update: String) {

        val manager =
            GlobalApplication.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(GlobalApplication.getApplicationContext(), MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            GlobalApplication.getApplicationContext(),
            0, notificationIntent, 0
        )


        val notification =
            NotificationCompat.Builder(
                GlobalApplication.getApplicationContext(),
                GlobalApplication.getApplicationContext().getString(R.string.channel_id)
            )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(GlobalApplication.getApplicationContext().getResources(), R.mipmap.ic_launcher))
                .setContentTitle("Last $country updates")
                .setContentText(update)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

                .build()

        manager.notify(1, notification)
    }

