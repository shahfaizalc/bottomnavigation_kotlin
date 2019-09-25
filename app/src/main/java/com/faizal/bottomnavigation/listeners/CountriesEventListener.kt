package com.faizal.bottomnavigation.listeners

import com.faizal.bottomnavigation.viewmodel.AdSearchModel

interface CountriesEventListener {

    fun onClickCountriesListItem(countriesViewModel : AdSearchModel, position: Int)


}