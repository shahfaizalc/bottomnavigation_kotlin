package com.reelme.app.pojos

import com.google.gson.annotations.SerializedName

class RelationshipStatusList {
    @SerializedName("Relationship")
    var chapters: List<RelationshipStatus>? = null
//    override fun toString(): String {
//        return "ChaptersInfo{" +
//                "gender = '" + chapters + '\'' +
//                "}"
//    }
}