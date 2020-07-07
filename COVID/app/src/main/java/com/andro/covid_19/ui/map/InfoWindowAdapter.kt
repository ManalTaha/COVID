package com.andro.covid_19.ui.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.andro.covid_19.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class InfoWindowAdapter (internal var context: Context?) : GoogleMap.InfoWindowAdapter {
    internal lateinit var inflater: LayoutInflater
    override fun getInfoContents(p0: Marker?): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker?): View {
        inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.info_window_layout, null)
        val cases = view.findViewById(R.id.info_window_cases) as TextView
        val death = view.findViewById(R.id.info_window_deaths) as TextView
        val recover = view.findViewById(R.id.info_window_recover) as TextView
        cases.text =  "Cases : ${marker?.title}"
        if(marker?.snippet != null)
        {
            val parts: List<String> = marker?.snippet!!.split(",")
            death.text = "Deaths: ${parts[0]}"
            recover.text = "Recovers: ${parts[1]}"
        }

        return view

    }

}