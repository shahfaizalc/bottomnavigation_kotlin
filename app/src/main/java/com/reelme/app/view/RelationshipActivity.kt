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
import com.reelme.app.adapter.RelationshipRecyclerViewAdapter
import com.reelme.app.databinding.RelationshipLayoutBinding
import com.reelme.app.listeners.EmptyResultListener
import com.reelme.app.pojos.RelationshipStatus
import com.reelme.app.pojos.UserModel
import com.reelme.app.util.GenericValues
import com.reelme.app.utils.FirbaseWriteHandlerActivity

class RelationshipActivity : AppCompatActivity() {

    lateinit var activity : Activity
    private var adapter: RelationshipRecyclerViewAdapter? = null
    private var binding: RelationshipLayoutBinding? = null
    private var selectedPosition = -1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        binding = DataBindingUtil.setContentView(this, R.layout.relationship_layout)
        binding!!.flightsRv.layoutManager = LinearLayoutManager(this)
        binding!!.flightsRv.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        getUserInfo()
        adapter = RelationshipRecyclerViewAdapter(prepareData(), this,selectedPosition)
        binding!!.flightsRv.adapter = adapter
        binding!!.nextBtn.setOnClickListener {
            if(adapter!!.getSelectedItem()>=0) {
                userDetails.relationshipStatus = prepareData()[adapter!!.getSelectedItem()].relationship
                setUserInfo()
            }
        }

        binding!!.skipBtn.setOnClickListener {
            userDetails.skipRelationshipStatus = true
            setUserInfo()
        }

    }

    private fun prepareData(): List<RelationshipStatus> {

        val values = GenericValues().getFileString("relationship.json", this)
        return GenericValues().getRelationshipStatusList(values,this)[0].chapters!!

    }

    lateinit var userDetails : UserModel
    private  var isEdit = false;

    private fun getUserInfo() {
        val sharedPreference = getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("USER_INFO", "");
        isEdit = sharedPreference.getBoolean("IS_EDIT",false)

        try {
            val auth = Gson().fromJson(coronaJson, UserModel::class.java)
            Log.d("Authentication token", auth.emailId!!)

            userDetails = (auth as UserModel)

            if(!isEdit && !userDetails.relationshipStatus.isNullOrEmpty()){
                startActivity(Intent(this, ChildrenActivity::class.java))
            }

            if (isEdit) {
                binding!!.skipBtn.visibility = View.GONE
                if (!userDetails.relationshipStatus.isNullOrEmpty()) {
                    val items = prepareData();

                    for ((index, value) in items.withIndex()) {
                        if (value.relationship.equals(userDetails.relationshipStatus)) {
                            selectedPosition = index
                        }
                    }

                } else {
                    selectedPosition = -1

                }
            }
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
        editor.putBoolean("IS_EDIT",false)
        editor.apply()

        FirbaseWriteHandlerActivity(this).updateUserInfo(userDetails, object : EmptyResultListener {
            override fun onSuccess() {
                binding!!.progressbar.visibility= View.INVISIBLE
                if(isEdit){
                    setResult(2, Intent())
                    finish()

                } else{
                    startActivity(Intent(activity, ChildrenActivity::class.java));
                }

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