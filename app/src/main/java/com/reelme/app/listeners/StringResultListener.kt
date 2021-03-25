package com.reelme.app.listeners

interface StringResultListener {

    fun onSuccess(url : String)
    fun onFailure(e: Exception)
}