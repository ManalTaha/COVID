package com.andro.covid_19.ui.map

import android.location.Address
import android.location.Geocoder
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andro.covid_19.R
import com.andro.covid_19.isNetworkConnected
import com.andro.retro.json_models.CountriesStat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.map_fragment.*


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMapLoadedCallback{

    private lateinit var viewModel: MapViewModel
    private lateinit var googleMap: GoogleMap
    private lateinit var gc: Geocoder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root=  inflater.inflate(R.layout.map_fragment, container, false)
        MapViewModel.context = this.context!!
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

        fab.setOnClickListener {
            if (isNetworkConnected(activity!!))
            {
                loadMarker()
                Snackbar.make(view!!, getString(R.string.wait_moment), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.action), null).show()
            }
            else
            {
                Snackbar.make(view!!, getString(R.string.connect_error), Snackbar.LENGTH_LONG)
                    .setAction((getString(R.string.action)), null).show()
            }
        }


    }


    override fun onResume() {
        super.onResume()
        mapView.onResume()

    }


    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
        }
        googleMap.setOnMapLoadedCallback(this)
        gc = Geocoder(context?.applicationContext)
        googleMap.setInfoWindowAdapter( InfoWindowAdapter(context?.applicationContext))
        if (!isNetworkConnected(activity!!))
        {
            Snackbar.make(view!!, getString(R.string.connect_error), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.action), null).show()
        }
        else
        {
            Snackbar.make(view!!,getString(R.string.wait_moment), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.action), null).show()
            loadMarker()
        }


    }

    private fun putEffectedCountries(countries: List<CountriesStat>, size:IntRange) {
        for (i in size) {
            putAffectedCountriesAsync().execute(countries[i])
        }
    }

    override fun onMapLoaded() {
    }
    private fun loadMarker()
    {
        viewModel.getCountriesData()
            .observe(viewLifecycleOwner, Observer<List<CountriesStat>> {

                putEffectedCountries(it, it.indices)

            })
    }
    private inner class putAffectedCountriesAsync : AsyncTask<CountriesStat, Void,  List<Address>>(){

        var countriesStat:CountriesStat? = null
        override fun doInBackground(vararg params: CountriesStat?): List<Address> {

            val addresses: List<Address> = try {
                gc.getFromLocationName(params[0]?.country_name, 3)
            } catch (e: Exception) {
                ArrayList<Address>()
            }
            countriesStat = params[0]
            return  addresses
        }


        override fun onPostExecute(result: List<Address>) {
            super.onPostExecute(result)
            if (result.isNotEmpty()) {
                googleMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            result.get(result.size - 1).latitude,
                            result.get(result.size - 1).longitude
                        )
                    ).title(countriesStat?.cases).snippet(countriesStat?.deaths+","+countriesStat?.total_recovered)
                )
            }
        }


    }




}


