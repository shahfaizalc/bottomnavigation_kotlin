package com.faizal.bottomnavigation.util

import com.faizal.bottomnavigation.model.Address
import com.faizal.bottomnavigation.model.PostAdModel

 fun offerPrice(postAdObj: PostAdModel) =
        (((postAdObj.ticketCount * postAdObj.price).toDouble()) -
                ((postAdObj.ticketCount * postAdObj.price).toDouble() * (postAdObj.discount.toDouble() / 100))).toString()

 fun getAddress(address: Address?) = address!!.locationname +
         ", " + address.streetName + ", " + address.town  + ", " + address.city + ", " + address.state
