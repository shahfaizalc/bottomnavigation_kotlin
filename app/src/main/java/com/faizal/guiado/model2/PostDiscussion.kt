package com.faizal.guiado.model2

import com.faizal.guiado.model.EventStatus

class PostDiscussion{

    var title: String? = null
    var keyWords: MutableList<Int>? = null
    var postedDate: String? = null
    var postedBy : String? = null
    var postedByName : String? = null
    var eventState: EventStatus = EventStatus.SHOWING
    var likes: ArrayList<Likes>? = null
    var bookmarks: ArrayList<Bookmarks>? = null
    var searchTags: List<String>? = null

}