package com.faizal.guiado.listeners

interface EmptyResultListener {

    fun onSuccess()
    fun onFailure(e: Exception)
}