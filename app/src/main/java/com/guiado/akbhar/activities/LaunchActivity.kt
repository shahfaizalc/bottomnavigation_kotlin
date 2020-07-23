package com.guiado.akbhar.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.guiado.akbhar.R

public class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_launch)
        val SPLASH_TIME_OUT: Long = 3000 //3 seconds

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, Main2Activity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)


    }
}