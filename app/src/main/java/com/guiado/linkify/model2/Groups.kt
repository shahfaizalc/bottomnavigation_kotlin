package com.guiado.linkify.model2

import com.guiado.linkify.model.EventStatus

class Groups{

    var title: String? = null
    var description: String? = null
    var postedDate: String? = null
    var postedBy : String? = null
    var postedByName : String? = null
    var members: ArrayList<Members>? = null
    var joinedBy: ArrayList<String>? = null
    var availability : Boolean = false
    var keyWords: MutableList<Int>? = null
    var eventState: EventStatus = EventStatus.SHOWING
    var comments: ArrayList<Comments>? = null
    var searchTags: List<String>? = null
}