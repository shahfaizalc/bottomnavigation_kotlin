package com.guiado.koodal.model2

import com.guiado.koodal.model.Address
import com.guiado.koodal.model.EventStatus

class PostEvents{

    var title: String? = null
    var desc: String? = null
    var address: Address? = null
    var keyWords: MutableList<Int>? = null
    var contactInfo : String? = null
    var expiryDate : String? = null
    var postedDate: String? = null
    var postedBy : String? = null
    var likes :Int? = 0
    var eventState: EventStatus = EventStatus.SHOWING
}