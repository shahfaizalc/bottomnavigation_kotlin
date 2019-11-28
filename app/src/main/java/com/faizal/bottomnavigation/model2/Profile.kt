package com.faizal.bottomnavigation.model2

import com.faizal.bottomnavigation.model.Address
import com.faizal.bottomnavigation.model.CoachItem

class Profile{

    var name: String? = null
    var email: String? = null
    var phone: String? = null
    var title: String? = null
    var address: Address? = null
    var moreInformation: String? = null
    var keyWords: MutableList<Int>? = null
    var availability : Boolean = false
    var profileImgUrl : String? = null
    var rating: Double = 0.0
    var views: Int = 0
    var desc : String? = null

}