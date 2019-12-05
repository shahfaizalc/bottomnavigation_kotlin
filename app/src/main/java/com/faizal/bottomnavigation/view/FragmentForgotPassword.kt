package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.FragmentForgotpasswordBinding
import com.faizal.bottomnavigation.databinding.FragmentSigninBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.viewmodel.ForgotPasswordViewModel
import com.faizal.bottomnavigation.viewmodel.SignInViewModel


class FragmentForgotPassword : BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: ForgotPasswordViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<FragmentForgotpasswordBinding>(inflater, R.layout.fragment_forgotpassword, container, false)
        areaViewModel = ForgotPasswordViewModel(this.context!!, this)
        binding.homeData = areaViewModel
        return binding.root
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
