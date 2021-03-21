package com.reelme.app.utils

import java.util.regex.Matcher
import java.util.regex.Pattern


class Validator {

    private var matcher: Matcher? = null

    /**
     * Validate hex with regular expression
     *
     * @param hex hex for validation
     * @return true valid hex, false invalid hex
     */
    fun validate(hex: String?, validator: EnumValidator): Boolean {

        if (null == hex) return false

        val pattern: Pattern
        when (validator) {
            EnumValidator.EMAIL_PATTERN -> {
                pattern = Pattern.compile(EMAIL_PATTERN)
                matcher = pattern.matcher(hex.toLowerCase())
            }
            EnumValidator.DATE_PATTERN -> {
                pattern = Pattern.compile(DATE_PATTERN)
                matcher = pattern.matcher(hex.toLowerCase())
            }
        }
        return matcher!!.matches()
    }

    companion object {

        private val EMAIL_PATTERN = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
        private val DATE_PATTERN = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$"
    }
}
