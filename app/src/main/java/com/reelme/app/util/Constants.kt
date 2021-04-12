package com.reelme.app.util

import com.reelme.app.view.SaleAllFragment
import com.reelme.app.view.SaleMenFragment
import com.reelme.app.view.SaleWomenFragment

object Constants {

    //List of fragments to add on TABS in order
    val SALE_FRAGMENTS = arrayOf(SaleMenFragment(), SaleAllFragment(), SaleWomenFragment())


    val FOLLOW_FRAGMENTS = arrayOf(SaleMenFragment(), SaleAllFragment())


    //key
    val SOLDOUT = "sold_out"

    /**
     * BASE URL
     */
    val BASE_URL = "https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/"
}
