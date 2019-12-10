package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.FragmentOnediscussionBinding
import com.faizal.bottomnavigation.databinding.FragmentProfileBinding
import com.faizal.bottomnavigation.databinding.FragmentWelcomeBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.viewmodel.OneDiscussionViewModel
import com.faizal.bottomnavigation.viewmodel.ProfileViewModel
import com.faizal.bottomnavigation.viewmodel.WelcomeViewModel
import com.google.firebase.auth.FirebaseAuth


class FragmentOneDiscussion : BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: OneDiscussionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val postAdObj  = arguments!!.getString(Constants.POSTAD_OBJECT)

        return bindView(inflater, container, postAdObj)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?, postAdObj: String): View {
        val binding = DataBindingUtil.inflate<FragmentOnediscussionBinding>(inflater, R.layout.fragment_onediscussion, container, false)
        areaViewModel = OneDiscussionViewModel(this.context!!, this,postAdObj)
        binding.mainDataModel = areaViewModel
        binding.countriesInfoModel = areaViewModel.listOfCoachings
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
