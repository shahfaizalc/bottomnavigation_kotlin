package com.faizal.guiado.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.faizal.guiado.R
import com.faizal.guiado.databinding.FragmentKeywordsBinding
import com.faizal.guiado.fragments.BaseFragment
import com.faizal.guiado.utils.Constants.POSTAD_OBJECT
import com.faizal.guiado.viewmodel.KeyWordsViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class FragmentKeyWords : BaseFragment() {

    lateinit var binding: FragmentKeywordsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val postAdObj = arguments!!.getString(POSTAD_OBJECT)

        return bindView(inflater, container, postAdObj)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: String): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_keywords, container, false)
        val areaViewModel = KeyWordsViewModel(activity!!, this, postAdObj)
        binding.postAdPricing = areaViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val chipGroup: ChipGroup = view.findViewById(R.id.chipGroup)
        val tagList = binding.postAdPricing?.listOfCoachings

        val extractKeyWords = binding.postAdPricing?.profile?.keyWords

        for (index in tagList!!.indices) {
            val tagName = tagList[index].categoryname
            val chip = layoutInflater.inflate(R.layout.chips_item, chipGroup, false) as Chip
            chip.text = tagName
            chip.isChecked =  if(extractKeyWords!=null)  extractKeyWords.contains(index+1) else  false

            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                Toast.makeText(activity?.applicationContext, "" + buttonView.text, Toast.LENGTH_LONG).show()
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
