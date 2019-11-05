package com.faizal.bottomnavigation.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.databinding.FragmentCountryBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.viewmodel.CountryViewModel


class FragmentCountry : BaseFragment() {

    @Transient
    lateinit internal var areaViewModel: CountryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return bindView(inflater, container)
    }

    private fun bindView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<FragmentCountryBinding>(inflater, R.layout.fragment_country, container, false)
        areaViewModel = CountryViewModel(this.context!!, this)
        binding.homeData = areaViewModel
        showInfoDialog(areaViewModel)
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

    fun showInfoDialog(countriesInfoModel: CountryViewModel) {
        val alert = CountriesInfoDialog()
        alert.showDialog(this.activity, countriesInfoModel)
    }
}
