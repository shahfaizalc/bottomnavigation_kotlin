package com.reelme.app.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView.CommaTokenizer
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentHobbiesBinding
import com.reelme.app.viewmodel.HobbiesViewModel


class FragmentHobbies : AppCompatActivity() {

    @Transient
    lateinit internal var areaViewModel: HobbiesViewModel
   // lateinit var adapter: ArrayAdapter<String>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       var binding : FragmentHobbiesBinding = DataBindingUtil.setContentView(this, R.layout.fragment_hobbies)
        areaViewModel = HobbiesViewModel(this, this)
        binding!!.homeData = areaViewModel
        setupAutoCompleteView(binding!!)    }




    private fun setupAutoCompleteView(mContentViewBinding: FragmentHobbiesBinding) {
        val animalList= mContentViewBinding!!.homeData!!.prepareData()
//        adapter  = ArrayAdapter<String>(
//                this, android.R.layout.simple_spinner_dropdown_item,
//                animalList)


        // In order to show the substring options in a dropdown, we need ArrayAdapter
        // and here it is using simple_list_item_1
        val randomArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, animalList)

        mContentViewBinding.flightsRv.setAdapter(randomArrayAdapter)
        mContentViewBinding.flightsRv.setTextColor(Color.WHITE)
        mContentViewBinding.flightsRv.threshold = 2
        mContentViewBinding.flightsRv.setTokenizer(CommaTokenizer())



        mContentViewBinding.flightsRv.onItemClickListener =
                AdapterView.OnItemClickListener { parent, arg1, position, id ->
                    val selected = parent.getItemAtPosition(position)
                    mContentViewBinding!!.homeData!!.posititonSelected = selected as String;
                    Log.d("posititonSelectedaa ", "posititonSelected$selected")

                }
    }

    override fun onResume() {
        super.onResume()
        areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
        areaViewModel.unRegisterListeners()
    }
}
