package com.reelme.app.util

import android.content.Context
import com.reelme.app.model2.Profile
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


fun <T : Any> T?.notNull(function: (it: T) -> Unit) {
    if (this != null) function(this)
}


