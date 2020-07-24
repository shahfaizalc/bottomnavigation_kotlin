package com.guiado.akbhar.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.guiado.akbhar.R
import com.guiado.akbhar.utils.Constants.LANGUAGE_ID
import com.guiado.akbhar.utils.SharedPreference
import com.guiado.akbhar.view.FragmentLanguage
import java.util.*

public class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_launch)
        val SPLASH_TIME_OUT: Long = 3000 //3 seconds

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            val pref = SharedPreference(this)
            var value = pref.getValueString(LANGUAGE_ID)

            if (value!!.length > 0) {
                startActivity(Intent(this, Main2Activity::class.java))
            } else {
                startActivity(Intent(this, FragmentLanguage::class.java))
            }
            val deviceLocale: String = Locale.getDefault().getLanguage()

            Log.d("tagger",""+ deviceLocale)

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)


    }
}