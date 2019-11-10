package com.faizal.bottomnavigation.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log

import com.faizal.bottomnavigation.R


/**
 */

open class BaseActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun initToolbar(toolbar: Toolbar, isBackEnabled: Boolean) {
        setSupportActionBar(toolbar)

        if (isBackEnabled) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        }
    }

    fun initToolbar(toolbar: Toolbar, title: String, isBackEnabled: Boolean) {

        setSupportActionBar(toolbar)

        if (isBackEnabled) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        }

        supportActionBar!!.title = title


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("dfadf","dfad");
    }
}
