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
import com.reelme.app.pojos.RelationshipStatus
import com.reelme.app.pojos.UserModel
import com.reelme.app.util.GenericValues
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

        binding!!.skipBtn.setOnClickListener {
            startActivity(Intent(this@RelationshipActivity, ChildrenActivity::class.java))
        }

        getUserInfo()
    }

    private fun prepareData(): List<RelationshipStatus> {

        val values = GenericValues().getFileString("relationship.json", this)
        return GenericValues().getRelationshipStatusList(values,this)[0].chapters!!

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
        userDetails.relationshipStatus = prepareData()[adapter!!.getSelectedItem()].relationship

        val gsonValue = Gson().toJson(userDetails)
        val sharedPreference =  getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("USER_INFO",gsonValue)
        editor.apply()

    }

}