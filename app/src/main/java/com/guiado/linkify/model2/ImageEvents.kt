package com.guiado.linkify.model2

import com.guiado.linkify.model.Address
import com.guiado.linkify.model.EventStatus

class ImageEvents{

    var imageId: String? = null
    var title: String? = null
    var startDate: String? = null
    var members: ArrayList<Bookmarks>? = null
    var eventState: EventStatus = EventStatus.SHOWING
    var postedDate: String? = null
    var postedBy : String? = null
    var postedByName : String? = null
    var bookmarkBy: ArrayList<String>? = null
}