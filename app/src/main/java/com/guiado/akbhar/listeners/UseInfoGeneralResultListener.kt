package com.guiado.akbhar.listeners

import com.guiado.akbhar.model2.Profile

interface UseInfoGeneralResultListener {
    fun onSuccess(userInfoGeneral: Profile)
    fun onFailure(e: Exception)

}