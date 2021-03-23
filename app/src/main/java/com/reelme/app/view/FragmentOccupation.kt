package com.reelme.app.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.reelme.app.R
import com.reelme.app.databinding.FragmentOccupationBinding
import com.reelme.app.model3.UserDetails
import com.reelme.app.util.GenericValues


class FragmentOccupation : AppCompatActivity() {
    private var binding: FragmentOccupationBinding? = null
    lateinit var adapter: ArrayAdapter<String>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_occupation)

        setupAutoCompleteView(binding!!)

        binding!!.nextBtn.setOnClickListener {

            if(posititonSelected >= 0) {
                setUserInfo()
                startActivity(Intent(this@FragmentOccupation, FragmentHobbies::class.java))
            }
        }
        getUserInfo()
    }

    var posititonSelected = -1

    private fun setupAutoCompleteView(mContentViewBinding: FragmentOccupationBinding) {
        val animalList=prepareData()
         adapter  = ArrayAdapter<String>(
                 this, android.R.layout.simple_spinner_item,
                 animalList)
        mContentViewBinding.flightsRv.setAdapter(adapter)
        mContentViewBinding.flightsRv.setTextColor(Color.WHITE)
        mContentViewBinding.flightsRv.threshold =1


        mContentViewBinding.flightsRv.onItemClickListener =
                AdapterView.OnItemClickListener { parent, arg1, position, id ->
                    val selected = parent.getItemAtPosition(position)
                    posititonSelected = position;
                    Log.d("posititonSelectedaa ", "posititonSelected$selected")

                }
    }

    private fun prepareData(): ArrayList<String> {

        val values = GenericValues().getFileString("Occupations.json", this)
        val occupations = GenericValues().getOccupationsList(values, this)[0].chapters!!

        val occupationList = ArrayList<String>()
        for(i in occupations){
            occupationList.add(i.occupations)
        }
       return occupationList;
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
        userDetails.religiousBeliefs = prepareData()[posititonSelected]

        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.apply()
    }

}