package com.faizal.guiado.model2

import com.faizal.guiado.model.Address
import com.faizal.guiado.model.EventStatus

class Events{

    var title: String? = null
    var description: String? = null
    var address : Address? = null
    var startDate: String? = null
    var endDate: String? = null

    var postedDate: String? = null
    var postedBy : String? = null
    var postedByName : String? = null
    var members: ArrayList<Bookmarks>? = null
    var keyWords: MutableList<Int>? = null
    var eventState: EventStatus = EventStatus.SHOWING
    var eventTime:String? = null
    var searchTags: List<String>? = null
}