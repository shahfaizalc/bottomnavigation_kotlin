package com.guiado.grads.listeners

import com.guiado.grads.model2.Profile

interface UseInfoGeneralResultListener {
    fun onSuccess(userInfoGeneral: Profile)
    fun onFailure(e: Exception)

}