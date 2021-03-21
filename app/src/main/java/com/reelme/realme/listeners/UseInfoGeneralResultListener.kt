package com.reelme.realme.listeners

import com.reelme.realme.model2.Profile

interface UseInfoGeneralResultListener {
    fun onSuccess(userInfoGeneral: Profile)
    fun onFailure(e: Exception)

}