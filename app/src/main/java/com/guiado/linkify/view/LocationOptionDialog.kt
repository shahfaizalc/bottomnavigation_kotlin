package com.guiado.linkify.view

import android.app.Dialog
import android.view.LayoutInflater
import android.view.Window
import androidx.fragment.app.FragmentActivity
import com.guiado.linkify.databinding.DialogLocationpickerBinding
import com.guiado.linkify.viewmodel.LocationViewModel

class LocationOptionDialog {

    fun showDialog(activity: FragmentActivity?, model: LocationViewModel) {
        val reviewBinding = DialogLocationpickerBinding.inflate(LayoutInflater.from(activity))
        val dialog = Dialog(activity!!)
        dialog.setCancelable(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(reviewBinding.root)
        reviewBinding.mainData = model
        reviewBinding.useCurrentLocation.setOnClickListener{
            reviewBinding.mainData!!.colorResource.value = true
            dialog.dismiss()}
        reviewBinding.skipLocation.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}
