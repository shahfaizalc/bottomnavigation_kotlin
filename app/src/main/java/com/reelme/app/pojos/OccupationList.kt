package com.reelme.app.pojos

import com.google.gson.annotations.SerializedName

class OccupationList {
    @SerializedName("Occupations")
    var chapters: List<Occupation>? = null
//    override fun toString(): String {
//        return "ChaptersInfo{" +
//                "gender = '" + chapters + '\'' +
//                "}"
//    }
}