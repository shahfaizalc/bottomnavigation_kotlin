package com.reelme.app.pojos

import com.google.gson.annotations.SerializedName

class GenderList {
    @SerializedName("Gender")
    var chapters: List<Gender>? = null
//    override fun toString(): String {
//        return "ChaptersInfo{" +
//                "gender = '" + chapters + '\'' +
//                "}"
//    }
}