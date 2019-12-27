package com.faizal.guiado.listeners

import com.faizal.guiado.model2.Profile

interface UseInfoGeneralResultListener {
    fun onSuccess(userInfoGeneral: Profile)
    fun onFailure(e: Exception)

}