package com.reelme.app.listeners

import com.reelme.app.model2.AdventuresTopics
import com.reelme.app.model2.BonusTopics
import com.reelme.app.model2.SkipTopics

interface SkipTopicsResultListener {

    fun onSuccess(url : List<SkipTopics>)
    fun onFailure(e: Exception)
}