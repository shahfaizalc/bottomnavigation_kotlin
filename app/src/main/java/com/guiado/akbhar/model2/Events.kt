package com.guiado.akbhar.model2

import com.guiado.akbhar.model.Address
import com.guiado.akbhar.model.EventStatus

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
    var bookmarkBy: ArrayList<String>? = null
    var keyWords: MutableList<Int>? = null
    var eventState: EventStatus = EventStatus.SHOWING
    var eventTime:String? = null
    var searchTags: List<String>? = null
}