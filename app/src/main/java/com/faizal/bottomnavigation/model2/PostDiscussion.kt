package com.faizal.bottomnavigation.model2

import com.faizal.bottomnavigation.model.Address
import com.faizal.bottomnavigation.model.CoachItem
import com.faizal.bottomnavigation.model.EventStatus

class PostDiscussion{

    var title: String? = null
    var keyWords: MutableList<Int>? = null
    var postedDate: String? = null
    var postedBy : String? = null
    var likes :String? = null
    var eventState: EventStatus = EventStatus.SHOWING
}