package com.guiado.akbhar.listeners

interface EmptyResultListener {

    fun onSuccess()
    fun onFailure(e: Exception)
}