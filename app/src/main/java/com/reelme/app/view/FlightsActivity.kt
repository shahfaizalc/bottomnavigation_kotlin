package com.reelme.app.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.reelme.app.R
import com.reelme.app.adapter.FlightsRecyclerViewAdapter
import com.reelme.app.databinding.FlightLayoutBinding
import com.reelme.app.model3.UserDetails
import com.reelme.app.pojos.Gender
import com.reelme.app.util.GenericValues
import java.util.*

class FlightsActivity : AppCompatActivity() {

    private var adapter: FlightsRecyclerViewAdapter? = null
    private var binding: FlightLayoutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.flight_layout)
        binding!!.flightsRv.layoutManager = LinearLayoutManager(this)
        binding!!.flightsRv.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = FlightsRecyclerViewAdapter(prepareData()!!, this)
        binding!!.flightsRv.adapter = adapter
        binding!!.nextBtn.setOnClickListener {

          if(adapter!!.getSelectedItem()>=0){
              setUserInfo()
              startActivity(Intent(this@FlightsActivity, RelationshipActivity::class.java))

          }
         }
        getUserInfo()
    }



    private fun prepareData(): List<Gender>? {

       val values = GenericValues().getFileString("gender.json", this)
        return GenericValues().getGender(values,this)[0].chapters
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
        userDetails.bio = prepareData()?.get(adapter!!.getSelectedItem())?.toString()

        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO",gsonValue)
        editor.apply()

    }

}