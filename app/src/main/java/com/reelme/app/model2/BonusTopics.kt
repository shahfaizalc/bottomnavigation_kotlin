package com.reelme.app.model2

import com.google.firebase.Timestamp

data class BonusTopics (
        val topicDescription: String = "",
        var enabled: Boolean = true,
        var points: Int = 0,
        var createdDate: Timestamp
)
