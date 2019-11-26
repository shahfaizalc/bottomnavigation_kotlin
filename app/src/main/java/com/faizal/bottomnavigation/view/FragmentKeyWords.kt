package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.FragmentKeywordsBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.utils.Constants.POSTAD_OBJECT
import com.faizal.bottomnavigation.viewmodel.KeyWordsViewModel
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
        val tagList = binding.postAdPricing?.roleAdapterAddress
        for (index in tagList!!.indices) {
            val tagName = tagList[index].categoryname
            val chip = layoutInflater.inflate(R.layout.chips_item, chipGroup, false) as Chip
            chip.text = tagName
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                Toast.makeText(activity?.applicationContext, "" + buttonView.text, Toast.LENGTH_LONG).show()
                Log.d("tAG", "" + index + " index " + buttonView.text);
            }

            chipGroup.addView(chip)
        }
    }

}
