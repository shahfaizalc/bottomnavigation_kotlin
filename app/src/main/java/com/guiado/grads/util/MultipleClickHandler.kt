package com.guiado.grads.util

import android.os.SystemClock

class MultipleClickHandler {

    companion object {
        var mLastClickTime: Long = 0

        fun handleMultipleClicks(): Boolean {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                return true
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            return false
        }
    }

}