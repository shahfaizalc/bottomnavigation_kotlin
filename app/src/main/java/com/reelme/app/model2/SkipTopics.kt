package com.reelme.app.model2

import com.google.firebase.Timestamp

data class SkipTopics (
        var createdDate: Number,
        var neverShow :Boolean =false,
        var showInEnd: Boolean = false,
        var topicId : String="",
        var uid:String=""
)
