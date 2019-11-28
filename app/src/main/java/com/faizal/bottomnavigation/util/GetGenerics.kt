package com.faizal.bottomnavigation.util

import android.provider.ContactsContract
import com.faizal.bottomnavigation.model.Address
import com.faizal.bottomnavigation.model.PostAdModel
import com.faizal.bottomnavigation.model2.Profile

//fun offerPrice(postAdObj: Profile) =
//        (((postAdObj.ticketCount * postAdObj.price).toDouble()) -
//                ((postAdObj.ticketCount * postAdObj.price).toDouble() * (postAdObj.discount.toDouble() / 100))).toString()

 fun getAddress(address: Address?) = address!!.locationname +
         ", " + address.streetName + ", " + address.town  + ", " + address.city + ", " + address.state

fun offerPrice(postAdObj: Profile) = (postAdObj.name)