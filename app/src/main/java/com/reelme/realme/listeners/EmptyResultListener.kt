package com.reelme.realme.listeners

interface EmptyResultListener {

    fun onSuccess()
    fun onFailure(e: Exception)
}