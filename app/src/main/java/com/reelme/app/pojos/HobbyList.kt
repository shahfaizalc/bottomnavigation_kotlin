package com.reelme.app.pojos

import com.google.gson.annotations.SerializedName

class HobbyList {
    @SerializedName("Hobbies")
    var chapters: List<Hobby>? = null
//    override fun toString(): String {
//        return "ChaptersInfo{" +
//                "gender = '" + chapters + '\'' +
//                "}"
//    }
}