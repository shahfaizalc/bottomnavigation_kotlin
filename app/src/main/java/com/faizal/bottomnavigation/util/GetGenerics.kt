package com.faizal.bottomnavigation.util

import com.faizal.bottomnavigation.model.Address
import com.faizal.bottomnavigation.model2.Profile
import java.text.SimpleDateFormat
import java.util.*

//fun offerPrice(postAdObj: Profile) =
//        (((postAdObj.ticketCount * postAdObj.price).toDouble()) -
//                ((postAdObj.ticketCount * postAdObj.price).toDouble() * (postAdObj.discount.toDouble() / 100))).toString()

 fun getAddress(address: Address?) = address!!.locationname +
         ", " + address.streetName + ", " + address.town  + ", " + address.city + ", " + address.state

fun offerPrice(postAdObj: Profile) = (postAdObj.name)

fun convertLongToTime(time: Long): String {
 val date = Date(time)
 val format = SimpleDateFormat("dd/MM/yyyy")
 return format.format(date)
}

fun <T : Any> T?.notNull(function: (it: T) -> Unit) {
 if (this != null) function(this)
}