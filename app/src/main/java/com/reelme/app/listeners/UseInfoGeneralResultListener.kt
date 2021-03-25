package com.reelme.app.listeners

import com.reelme.app.model2.Profile
import com.reelme.app.pojos.UserModel

interface UseInfoGeneralResultListener {
    fun onSuccess(userInfoGeneral: UserModel)
    fun onFailure(e: Exception)

}