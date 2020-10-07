package com.guiado.linkify.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.guiado.linkify.activities.Main2Activity
import com.guiado.linkify.view.FragmentVerification
import com.guiado.linkify.view.FragmentWelcome

public class LaunchActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            if ( mAuth.currentUser!!.isEmailVerified) {
                finish()
                startActivity(Intent(this, Main2Activity::class.java))
            } else{
                finish()
                startActivity(Intent(this, FragmentVerification::class.java))

            }
        } else {
            finish()
            startActivity(Intent(this, FragmentWelcome::class.java))
        }
    }
}