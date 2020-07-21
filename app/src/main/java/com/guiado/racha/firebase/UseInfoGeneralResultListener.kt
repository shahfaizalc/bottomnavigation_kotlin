package com.guiado.racha.firebase

import com.guiado.racha.room.Feed

interface UseInfoGeneralResultListener {
    fun onSuccess(userInfoGeneral: ArrayList<Feed>)
    fun onFailure(e: Exception)
}