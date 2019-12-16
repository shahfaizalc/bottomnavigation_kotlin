package com.faizal.bottomnavigation.model2

import com.faizal.bottomnavigation.model.Address
import com.faizal.bottomnavigation.model.CoachItem
import com.faizal.bottomnavigation.model.EventStatus

class Groups{

    var title: String? = null
    var keyWords: MutableList<Int>? = null
    var postedDate: String? = null
    var postedBy : String? = null
    var postedByName : String? = null
    var eventState: EventStatus = EventStatus.SHOWING
    var comments: ArrayList<Comments>? = null
    var likes: ArrayList<Likes>? = null
    var bookmarks: ArrayList<Bookmarks>? = null

}