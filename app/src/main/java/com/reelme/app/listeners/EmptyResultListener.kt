package com.reelme.app.listeners

interface EmptyResultListener {

    fun onSuccess()
    fun onFailure(e: Exception)
}