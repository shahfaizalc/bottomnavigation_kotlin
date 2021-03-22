package com.reelme.app.pojos

import com.google.gson.annotations.SerializedName

class RelegionList {
    @SerializedName("Religious")
    var chapters: List<ReligiousBelief>? = null
//    override fun toString(): String {
//        return "ChaptersInfo{" +
//                "gender = '" + chapters + '\'' +
//                "}"
//    }
}