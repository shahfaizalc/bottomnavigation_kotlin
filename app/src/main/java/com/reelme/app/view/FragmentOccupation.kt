package com.reelme.app.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_occupation)
//        binding!!.flightsRv.layoutManager = LinearLayoutManager(this)
//        binding!!.flightsRv.addItemDecoration(
//                DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        setupAutoCompleteView(binding!!)

//        binding!!.nextBtn.setOnClickListener {
//            if(adapter!!.getSelectedItem()>=0) {
//                setUserInfo()
//                startActivity(Intent(this@FragmentOccupation, FragmentHobbies::class.java))            }
//        }
        getUserInfo()

    }

    private fun setupAutoCompleteView(mContentViewBinding: FragmentOccupationBinding) {
        val animalList=prepareData()
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,
                animalList )
        mContentViewBinding.flightsRv.setAdapter(adapter)
        mContentViewBinding.flightsRv.setTextColor(Color.WHITE)
        mContentViewBinding.flightsRv.threshold =1
        mContentViewBinding.flightsRv.onItemClickListener =
                AdapterView.OnItemClickListener { parent, arg1, position, id ->
                    //TODO: You can your own logic.
                }

    }



    fun prepareData(): ArrayList<String> {

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
     //   userDetails.religiousBeliefs = prepareData()[adapter!!.getSelectedItem()].toString()

        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO", gsonValue)
        editor.apply()

    }


}