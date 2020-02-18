package com.guiado.koodal.listeners

interface EmptyResultListener {

    fun onSuccess()
    fun onFailure(e: Exception)
}