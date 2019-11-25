package com.faizal.bottomnavigation.listeners

import com.faizal.bottomnavigation.model2.Profile

interface UseInfoGeneralResultListener {
    fun onSuccess(userInfoGeneral: Profile)
    fun onFailure(e: Exception)

}