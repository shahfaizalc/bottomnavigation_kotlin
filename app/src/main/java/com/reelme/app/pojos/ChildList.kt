package com.reelme.app.pojos

import com.google.gson.annotations.SerializedName

class ChildList {
    @SerializedName("Children")
    var chapters: List<Child>? = null
//    override fun toString(): String {
//        return "ChaptersInfo{" +
//                "gender = '" + chapters + '\'' +
//                "}"
//    }
}