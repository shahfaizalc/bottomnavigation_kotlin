package com.faizal.bottomnavigation.util

import android.content.Context
import com.faizal.bottomnavigation.model.Address
import com.faizal.bottomnavigation.model.CoachItem
import java.text.SimpleDateFormat
import java.util.*

//fun offerPrice(postAdObj: Profile) =
//        (((postAdObj.ticketCount * postAdObj.price).toDouble()) -
//                ((postAdObj.ticketCount * postAdObj.price).toDouble() * (postAdObj.discount.toDouble() / 100))).toString()

fun getAddress(address: Address?) = address!!.locationname +
        ", " + address.streetName + ", " + address.town + ", " + address.city + ", " + address.state


fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.format(date)
}

fun <T : Any> T?.notNull(function: (it: T) -> Unit) {
    if (this != null) function(this)
}

fun getKeys(keyWords: MutableList<Int>?, context: Context): String? {

    var keyTag = "";

    val keysCoach: ArrayList<CoachItem> = readAutoFillItems(context)

    val numbersIterator = keyWords!!.iterator()
    numbersIterator.let {
        while (numbersIterator.hasNext()) {
            var value = (numbersIterator.next())
            keyTag += " " + keysCoach.get(value).categoryname

        }
    }
    return keyTag;

}


private fun readAutoFillItems(context: Context): ArrayList<CoachItem> {
    val c = GenericValues()
    return c.readCoachItems(context)
}