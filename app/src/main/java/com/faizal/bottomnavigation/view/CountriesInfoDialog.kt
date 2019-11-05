package com.faizal.bottomnavigation.view

import android.app.Dialog
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.Window
import com.faizal.bottomnavigation.databinding.DialogCountryinfoBinding
import com.faizal.bottomnavigation.viewmodel.CountryViewModel

class CountriesInfoDialog {

    fun showDialog(activity: FragmentActivity?, model: CountryViewModel) {
        val reviewBinding = DialogCountryinfoBinding.inflate(LayoutInflater.from(activity))
        val dialog = Dialog(activity)
        dialog.setCancelable(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(reviewBinding.root)
        reviewBinding.mainData = model
        reviewBinding.skipLocation.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}
