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
import com.reelme.app.viewmodel.OccupationViewModel
import com.reelme.app.viewmodel.UsernameViewModel


class FragmentOccupation : AppCompatActivity() {


    @Transient
    lateinit internal var areaViewModel: OccupationViewModel

    lateinit var adapter: ArrayAdapter<String>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding : FragmentOccupationBinding = DataBindingUtil.setContentView(this, R.layout.fragment_occupation)
        areaViewModel = OccupationViewModel(this, this)
        binding!!.homeData = areaViewModel
        setupAutoCompleteView(binding!!)

    }


    private fun setupAutoCompleteView(mContentViewBinding: FragmentOccupationBinding) {
        val animalList= mContentViewBinding!!.homeData!!.prepareData()
         adapter  = ArrayAdapter<String>(
                 this, android.R.layout.simple_spinner_item,
                 animalList)
        mContentViewBinding.flightsRv.setAdapter(adapter)
        mContentViewBinding.flightsRv.setTextColor(Color.WHITE)
        mContentViewBinding.flightsRv.threshold =1


        mContentViewBinding.flightsRv.onItemClickListener =
                AdapterView.OnItemClickListener { parent, arg1, position, id ->
                    val selected = parent.getItemAtPosition(position)
                    mContentViewBinding!!.homeData!!.posititonSelected = selected as String
                    Log.d("posititonSelectedaa ", "posititonSelected$selected")

                }
    }
}