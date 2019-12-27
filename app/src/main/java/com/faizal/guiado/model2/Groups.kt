package com.faizal.guiado.model2

import com.faizal.guiado.model.EventStatus

class Groups{

    var title: String? = null
    var description: String? = null
    var postedDate: String? = null
    var postedBy : String? = null
    var postedByName : String? = null
    var members: ArrayList<Bookmarks>? = null
    var availability : Boolean = false
    var keyWords: MutableList<Int>? = null
    var eventState: EventStatus = EventStatus.SHOWING
    var comments: ArrayList<Comments>? = null
    var searchTags: List<String>? = null
}