package com.reelme.app.view

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.*
import com.reelme.app.viewmodel.*


class FragmentEmailAddress : Activity() {

    @Transient
    internal lateinit var areaViewModel: EmailAddressViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentEmailaddressBinding = DataBindingUtil.setContentView(this, R.layout.fragment_emailaddress)
        areaViewModel = EmailAddressViewModel(this, this)
        binding.homeData = areaViewModel

//        val spannable = SpannableStringBuilder(resources.getString(R.string.home_emailskip))
//        spannable.setSpan(
//                ForegroundColorSpan(Color.RED),
//                8, // start
//                12, // end
//                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
//        )

        val spannable = SpannableString(resources.getString(R.string.home_emailskip))
        spannable.setSpan(
                ForegroundColorSpan(Color.WHITE),
                37, resources.getString(R.string.home_emailskip).length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.terms.text = spannable
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
