package com.guiado.linkify.listeners

interface EmptyResultListener {

    fun onSuccess()
    fun onFailure(e: Exception)
}