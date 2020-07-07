package com.andro.covid_19.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.andro.covid_19.R
import com.andro.covid_19.ui.instructions.instructions

class splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
          startActivity(Intent(this@splash,instructions::class.java))
            finish()
        }, 1500)
    }

}


