package com.andro.covid_19.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andro.covid_19.AlarmManagerHandler
import com.andro.covid_19.R
import com.andro.covid_19.SavedPreferences
import com.andro.covid_19.isNetworkConnected
import com.andro.retro.json_models.AllAffectedCountries
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {
    private var countryName: String = "USA"
    private lateinit var settingsViewModel: SettingsViewModel
    private var chosenPeriod: String? = "None"
    private var countryNumberInArray = -1
    private var intervalNo = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        SettingsViewModel.context = this.context!!
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        return root
    }


    override fun onStart() {
        super.onStart()
        countryNumberInArray = SavedPreferences.getCountry()!!
        controlView()
        settingSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                context!!,
                R.color.colorPrimary
            )
        )
        settingSwipeRefreshLayout.setOnRefreshListener {
            controlView()

        }

    }

    private fun controlView() {
        if (isNetworkConnected(activity!!)) {

            settingLayout.visibility = View.VISIBLE
            setting_no_connection.visibility = View.INVISIBLE

            settingsViewModel.getAllAffectedCountries()
                .observe(viewLifecycleOwner, Observer<AllAffectedCountries> {
                    val array: ArrayList<String> = ArrayList()
                    for (i in 0 until it.affected_countries.size - 1) {
                        if (it.affected_countries[i] != "") {
                            array.add(it.affected_countries[i])
                        }
                    }
                    array.let { it1 -> setupCountrySpinner(it1) }


                })
            setupIntervalSpinner()
            setupSaveButton()
            settingSwipeRefreshLayout.isRefreshing = false

        } else {
            settingLayout.visibility = View.INVISIBLE
            setting_no_connection.visibility = View.VISIBLE
            settingSwipeRefreshLayout.isRefreshing = false

        }


    }

    private fun setupIntervalSpinner() {
        SavedPreferences.getInterval()?.let { intervalSpinner.setSelection(it) }
        intervalSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                countryName = getString(R.string.usa)
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                chosenPeriod = parent?.getItemAtPosition(position).toString()
                intervalNo = position

            }

        }
    }

    fun setupCountrySpinner(countriesArr: List<String>) {
        notiCountry.setTitle(getString(R.string.select_country))
        notiCountry.setPositiveButton(getString(R.string.ok));

        var adapter = ArrayAdapter(
            activity!!,
            R.layout.my_spinner_item,
            countriesArr
        )
        notiCountry.adapter = adapter
        notiCountry.setSelection(countryNumberInArray)
        notiCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                countryName = getString(R.string.usa)
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                countryName = parent?.getItemAtPosition(position).toString()
                countryNumberInArray = position

            }

        }

    }

    fun setupSaveButton() {

        saveBtn.setOnClickListener {
            if (isNetworkConnected(activity!!)) {
                if (chosenPeriod == getString(R.string.none)) {
                    AlarmManagerHandler.cancelAlarm(countryName)
                    Snackbar.make(
                        view!!,
                        "Subscribe cancelled on $countryName",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(getString(R.string.action), null).show()

                } else {
                    chosenPeriod?.let { it1 ->
                        AlarmManagerHandler.setAlarmManager(
                            countryName,
                            countryNumberInArray,
                            it1,
                            intervalNo
                        )
                    }
                    Snackbar.make(
                        view!!,
                        "Subscribe successfully on $countryName updates",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(getString(R.string.action), null).show()

                }

            } else {
                Snackbar.make(view!!, getString(R.string.connect_error), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.action), null).show()

            }


        }
    }

}
