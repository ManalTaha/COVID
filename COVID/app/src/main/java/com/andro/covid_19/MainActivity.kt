package com.andro.covid_19

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.navigation.ui.AppBarConfiguration
import com.andro.covid_19.ui.history.HistoryFragment
import com.andro.covid_19.ui.home.HomeFragment
import com.andro.covid_19.ui.settings.SettingsFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.show()


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle =
            ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val navView: NavigationView = findViewById(R.id.nav_view)
        val home = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, home)
            .commit()
        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            var selectedFregment: Fragment? = null
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    selectedFregment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, selectedFregment).commit()
                }
                R.id.nav_history -> {

                        selectedFregment =HistoryFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment, selectedFregment).commit()

                }

                R.id.nav_settings -> {
                    selectedFregment = SettingsFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, selectedFregment).commit()
                }
            }
            drawerLayout.closeDrawers()
            true
        })

    }
}
