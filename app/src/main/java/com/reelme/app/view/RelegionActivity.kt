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
import com.reelme.app.pojos.ReligiousBelief
import com.reelme.app.pojos.UserModel
import com.reelme.app.util.GenericValues
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

        binding!!.skipBtn.setOnClickListener {
            startActivity(Intent(this@RelegionActivity, FragmentHobbies::class.java))
        }


        getUserInfo()

    }

    fun prepareData(): List<ReligiousBelief> {

        val values = GenericValues().getFileString("religion.json", this)
        return GenericValues().getRelegionList(values,this)[0].chapters!!

    }

    lateinit var userDetails : UserModel

    private fun getUserInfo() {
        val sharedPreference = getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d("Authentication token", auth.emailId)
            userDetails = (auth as UserModel)
        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")
        }
    }

    private fun setUserInfo(){
        userDetails.religiousBeliefs = prepareData()[adapter!!.getSelectedItem()].religious

        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO",gsonValue)
        editor.apply()

    }


}