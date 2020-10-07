package com.nioneer.nioneer.listeners

interface EmptyResultListener {

    fun onSuccess()
    fun onFailure(e: Exception)
}