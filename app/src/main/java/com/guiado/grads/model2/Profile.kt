package com.guiado.grads.model2

import com.guiado.grads.model.Address

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
    var followers: ArrayList<Follow>? = null
    var following: ArrayList<Follow>? = null

}