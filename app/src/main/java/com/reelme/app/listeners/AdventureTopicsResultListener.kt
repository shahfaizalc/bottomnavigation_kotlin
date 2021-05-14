package com.reelme.app.listeners

import com.reelme.app.model2.AdventuresTopics
import com.reelme.app.model2.BonusTopics

interface AdventureTopicsResultListener {

    fun onSuccess(adventureList : List<AdventuresTopics>)
    fun onFailure(e: Exception)
}