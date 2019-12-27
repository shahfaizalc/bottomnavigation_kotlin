package com.guiado.grads.listeners

interface EmptyResultListener {

    fun onSuccess()
    fun onFailure(e: Exception)
}