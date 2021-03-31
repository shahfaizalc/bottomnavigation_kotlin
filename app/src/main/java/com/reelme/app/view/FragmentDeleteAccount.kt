package com.reelme.app.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.reelme.app.R
import com.reelme.app.databinding.FragmentDeleteaccountBinding
import com.reelme.app.databinding.FragmentDemographicsBinding
import com.reelme.app.databinding.FragmentFullnameBinding
import com.reelme.app.viewmodel.DeleteAccountViewModel
import com.reelme.app.viewmodel.DemographicsViewModel
import com.reelme.app.viewmodel.EnterFullNameViewModel


class FragmentDeleteAccount : Activity() {

    @Transient
    internal lateinit var areaViewModel: DeleteAccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : FragmentDeleteaccountBinding = DataBindingUtil.setContentView(this, R.layout.fragment_deleteaccount)
        areaViewModel = DeleteAccountViewModel(this, this)
        binding.homeData = areaViewModel

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
