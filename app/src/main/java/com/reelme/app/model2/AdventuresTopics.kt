package com.reelme.app.model2

import com.google.firebase.Timestamp

data class AdventuresTopics (
        val topicDescription: String = "",
        var enabled: Boolean = true,
        var points: Int = 0,
        var createdDate: Timestamp,
        var maskedPoints: Int =0,
        var type :String =""

)
