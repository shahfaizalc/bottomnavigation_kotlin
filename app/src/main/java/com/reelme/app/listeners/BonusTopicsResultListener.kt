package com.reelme.app.listeners

import com.reelme.app.model2.BonusTopics

interface BonusTopicsResultListener {

    fun onSuccess(url : List<BonusTopics>)
    fun onFailure(e: Exception)
}