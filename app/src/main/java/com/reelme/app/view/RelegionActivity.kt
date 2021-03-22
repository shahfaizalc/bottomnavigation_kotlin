package com.reelme.app.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.reelme.app.R
import com.reelme.app.adapter.RelegiousRecyclerViewAdapter
import com.reelme.app.databinding.RelegiousLayoutBinding
import com.reelme.app.model.Flight
import com.reelme.app.model3.UserDetails
import java.util.*

class RelegionActivity : AppCompatActivity() {
    private var adapter: RelegiousRecyclerViewAdapter? = null
    private var binding: RelegiousLayoutBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.relegious_layout)
        binding!!.flightsRv.layoutManager = LinearLayoutManager(this)
        binding!!.flightsRv.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = RelegiousRecyclerViewAdapter(prepareData(), this)
        binding!!.flightsRv.adapter = adapter

        binding!!.nextBtn.setOnClickListener {
            if(adapter!!.getSelectedItem()>=0) {
                setUserInfo()
                startActivity(Intent(this@RelegionActivity, FragmentHobbies::class.java))            }
        }
        getUserInfo()

    }

    fun prepareData(): List<Flight> {
        val flights: MutableList<Flight> = ArrayList()
        var flight = Flight("Atheist")
        flights.add(flight)
        flight = Flight("Agnostic")
        flights.add(flight)
        flight = Flight("Buddist")
        flights.add(flight)
        flight = Flight("Christian")
        flights.add(flight)
        flight = Flight("Catholic")
        flights.add(flight)
        flight = Flight("Hindu")
        flights.add(flight)
        flight = Flight("Islamic")
        flights.add(flight)
        flight = Flight("Jewish")
        flights.add(flight)
        flight = Flight("Other")
        flights.add(flight)
        return flights
    }

    lateinit var userDetails : UserDetails

    private fun getUserInfo() {
        val sharedPreference = getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");

        try {
            val auth = Gson().fromJson(coronaJson, UserDetails::class.java)
            Log.d("Authentication token", auth.emailId)
            userDetails = (auth as UserDetails)
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }
    }

    private fun setUserInfo(){
        userDetails.religiousBeliefs = prepareData()[adapter!!.getSelectedItem()].toString()

        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO",gsonValue)
        editor.apply()

    }


}