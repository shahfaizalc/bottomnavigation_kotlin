package com.guiado.linkify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guiado.linkify.R
import com.guiado.linkify.databinding.FragmentWelcomeBinding
import com.guiado.linkify.fragments.BaseFragment
import com.guiado.linkify.viewmodel.WelcomeViewModel
import com.google.firebase.auth.FirebaseAuth


class FragmentWelcome : BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: WelcomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<FragmentWelcomeBinding>(inflater, R.layout.fragment_welcome, container, false)
        areaViewModel = WelcomeViewModel(this.context!!, this)
        binding.homeData = areaViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        mAuth.currentUser?.run {
            if(mAuth.currentUser?.isEmailVerified!!)
                isUserSignedIN()
            else
                isuserVerified()
        }

   }


   fun isUserSignedIN(){
        val fragment = FragmentProfile()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        this.mFragmentNavigation.replaceFragment(this.newInstance(0,fragment,bundle));
    }

    fun isuserVerified(){
        val fragment = FragmentVerification()
        val bundle = Bundle()
        fragment.setArguments(bundle)
        this.mFragmentNavigation.replaceFragment(this.newInstance(0,fragment,bundle));
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
