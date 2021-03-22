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
import com.reelme.app.adapter.RelationshipRecyclerViewAdapter
import com.reelme.app.databinding.RelationshipLayoutBinding
import com.reelme.app.model.Flight
import com.reelme.app.model3.UserDetails
import java.util.*

class RelationshipActivity : AppCompatActivity() {

    private var adapter: RelationshipRecyclerViewAdapter? = null
    private var binding: RelationshipLayoutBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.relationship_layout)
        binding!!.flightsRv.layoutManager = LinearLayoutManager(this)
        binding!!.flightsRv.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = RelationshipRecyclerViewAdapter(prepareData(), this)
        binding!!.flightsRv.adapter = adapter
        binding!!.nextBtn.setOnClickListener {
            if(adapter!!.getSelectedItem()>=0) {
                setUserInfo()
                startActivity(Intent(this@RelationshipActivity, ChildrenActivity::class.java))
            }
        }
        getUserInfo()
    }

    private fun prepareData(): List<Flight> {
        val flights: MutableList<Flight> = ArrayList()
        var flight = Flight("Single")
        flights.add(flight)
        flight = Flight("Married")
        flights.add(flight)
        flight = Flight("Domestic Partnership")
        flights.add(flight)
        flight = Flight("Civil Union")
        flights.add(flight)
        flight = Flight("Divorced")
        flights.add(flight)
        flight = Flight("Separated")
        flights.add(flight)
        flight = Flight("Widowed")
        flights.add(flight)
        flight = Flight("It's Complicated")
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
        userDetails.relationshipStatus = prepareData()[adapter!!.getSelectedItem()].toString()

        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO",gsonValue)
        editor.apply()

    }

}