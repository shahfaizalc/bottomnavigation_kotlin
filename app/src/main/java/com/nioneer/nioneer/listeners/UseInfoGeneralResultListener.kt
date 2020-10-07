package com.nioneer.nioneer.listeners

import com.nioneer.nioneer.model2.Profile

interface UseInfoGeneralResultListener {
    fun onSuccess(userInfoGeneral: Profile)
    fun onFailure(e: Exception)

}