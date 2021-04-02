package com.reelme.app.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.reelme.app.R
import com.reelme.app.databinding.FragmentVerifymobileBinding
import com.reelme.app.listeners.StringResultListener
import com.reelme.app.utils.OTP_Receiver
import com.reelme.app.viewmodel.VerifyMobileViewModel


class FragmentVerifyMobile : AppCompatActivity() {
    private val REQ_USER_CONSENT = 200
    @Transient
    lateinit internal var areaViewModel: VerifyMobileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : FragmentVerifymobileBinding = DataBindingUtil.setContentView(this, R.layout.fragment_verifymobile)
        areaViewModel = VerifyMobileViewModel(this, this)
        binding.homeData = areaViewModel
        requestsmspermission()

        OTP_Receiver().setEditText(object: StringResultListener {

            override fun onSuccess(url: String) {
                binding.homeData!!.smsotp = url
            }

            override fun onFailure(e: Exception) {

            }
        })

    }

    override fun onResume() {
        super.onResume()
        areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
        areaViewModel.unRegisterListeners()

    }

    private fun requestsmspermission() {
        val smspermission: String = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, smspermission)
        //check if read SMS permission is granted or not
        if (grant != PackageManager.PERMISSION_GRANTED) {
            val permission_list = arrayOfNulls<String>(1)
            permission_list[0] = smspermission
            ActivityCompat.requestPermissions(this, permission_list, 1)
        }
    }





}
