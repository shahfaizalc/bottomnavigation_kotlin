package com.reelme.app.util

import com.reelme.app.view.*

object Constants {

    //List of fragments to add on TABS in order
    val SALE_FRAGMENTS = arrayOf(SaleMenFragment(), SaleAllFragment(), SaleWomenFragment())


    val FOLLOW_FRAGMENTS = arrayOf(FragmentMyFollow(), FragmentMyFollow())


    //key
    val SOLDOUT = "sold_out"

    /**
     * BASE URL
     */
    val BASE_URL = "https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/"
}
