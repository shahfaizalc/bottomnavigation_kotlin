package com.reelme.app.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.reelme.app.R
import com.reelme.app.adapter.FlightsRecyclerViewAdapter
import com.reelme.app.databinding.FlightLayoutBinding
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.pojos.Gender
import com.reelme.app.pojos.UserModel
import com.reelme.app.util.GenericValues
import com.reelme.app.utils.FirbaseWriteHandlerActivity
import java.util.*

class FlightsActivity : Activity() {

    lateinit var activity  : Activity

    private var adapter: FlightsRecyclerViewAdapter? = null
    private var binding: FlightLayoutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        binding = DataBindingUtil.setContentView(this, R.layout.flight_layout)
        binding!!.flightsRv.layoutManager = LinearLayoutManager(this)
        binding!!.flightsRv.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = FlightsRecyclerViewAdapter(prepareData()!!, this)
        binding!!.flightsRv.adapter = adapter
        binding!!.nextBtn.setOnClickListener {

          if(adapter!!.getSelectedItem()>=0){
              userDetails.gender = prepareData()?.get(adapter!!.getSelectedItem())?.gender
              setUserInfo()

          }
         }

        binding!!.skipBtn.setOnClickListener {
            userDetails.skipGender = true
            setUserInfo()
        }



        getUserInfo()
    }



    private fun prepareData(): List<Gender>? {

       val values = GenericValues().getFileString("gender.json", this)
        return GenericValues().getGender(values,this)[0].chapters
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

        binding!!.progressbar.visibility= View.VISIBLE

        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO",gsonValue)
        editor.apply()

        FirbaseWriteHandlerActivity(this).updateUserInfo(userDetails, object : EmptyResultListener {
            override fun onSuccess() {
                binding!!.progressbar.visibility= View.INVISIBLE
                startActivity(Intent(activity, RelationshipActivity::class.java));
                Log.d("Authenticaiton token", "onSuccess")
                Toast.makeText(activity, "we have successfully saved your profile", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

            }

            override fun onFailure(e: Exception) {
                binding!!.progressbar.visibility= View.INVISIBLE
                //   fragmentSignin.startActivity(Intent(fragmentSignin, FragmentHomePage::class.java));
                Log.d("Authenticaiton token", "Exception"+e)
                Toast.makeText(activity, "Failed to save your profile.. please try again later", Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }

            }
        })
    }

}