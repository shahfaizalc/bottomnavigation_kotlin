package com.guiado.linkify.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentKeywordsBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.utils.Constants.POSTAD_OBJECT
import com.guiado.linkify.viewmodel.KeyWordsViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class FragmentKeyWords : Activity() {

    lateinit var binding: FragmentKeywordsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val postAdObj = intent.extras!!.getString(POSTAD_OBJECT)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_keywords)
        val areaViewModel = KeyWordsViewModel(this, postAdObj!!)
        binding.postAdPricing = areaViewModel
       val chipGroup: ChipGroup = findViewById(R.id.chipGroup)
        val tagList = binding.postAdPricing?.listOfCoachings

        val extractKeyWords = binding.postAdPricing?.profile?.keyWords

        for (index in tagList!!.indices) {
            val tagName = tagList[index].categoryname
            val chip = layoutInflater.inflate(R.layout.chips_item, chipGroup, false) as Chip
            chip.text = tagName
            chip.isChecked =  if(extractKeyWords!=null)  extractKeyWords.contains(index+1) else  false

            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                Toast.makeText(this?.applicationContext, "" + buttonView.text, Toast.LENGTH_LONG).show()
                Log.d("tAG", tagList[index].categorycode + " " + tagList[index].categoryname);
                if (isChecked) {
                    binding.postAdPricing?.listOfCoachingsSelected?.add(tagList[index].categorycode.toInt())
                } else {
                    binding.postAdPricing?.listOfCoachingsSelected?.remove(tagList[index].categorycode.toInt())
                }
                Log.d("tAG", " "+binding.postAdPricing!!.listOfCoachingsSelected);

            }

            chipGroup.addView(chip)
        }
    }

}
