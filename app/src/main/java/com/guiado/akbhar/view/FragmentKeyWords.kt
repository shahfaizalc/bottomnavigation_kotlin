package com.guiado.akbhar.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.guiado.akbhar.R
import com.guiado.akbhar.databinding.FragmentKeywordsBinding
import com.guiado.akbhar.utils.Constants.POSTAD_OBJECT
import com.guiado.akbhar.viewmodel.KeyWordsViewModel
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
