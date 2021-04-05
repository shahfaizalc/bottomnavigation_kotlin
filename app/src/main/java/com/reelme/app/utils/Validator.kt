package com.reelme.app.utils

import android.util.Log
import com.reelme.app.pojos.UserModel
import com.reelme.app.utils.Validator.Companion.USER_NAME_PATTERN
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
            EnumValidator.USER_NAME_PATTERN -> {
                pattern = Pattern.compile(USER_NAME_PATTERN)
                matcher = pattern.matcher(hex.toLowerCase())
            }
            EnumValidator.NAME_PATTERN -> {
                pattern = Pattern.compile(NAME_PATTERN)
                matcher = pattern.matcher(hex.toLowerCase())
            }
        }
        return matcher!!.matches()
    }


    fun profileRate( user : UserModel): Int {

        val items = 14;
        var count =14;

        if (user.phoneNumber.isNullOrEmpty()){
            count--;
        }

          if (user.emailId.isNullOrEmpty()){
            count--;

        }
           if (user.firstName.isNullOrEmpty()){
            count--;

        }
          if (user.secondName.isNullOrEmpty()){
            count--;

        }
          if (user.dob.isNullOrEmpty()){
            count--;

        }
          if (user.username.isNullOrEmpty()){
            count--;

        }
           if (user.profilePic.isNullOrEmpty()){
            count--;

        }
          if (user.bio.isNullOrEmpty()){
            count--;

        }
           if (user.gender.isNullOrEmpty()){
            count--;

        }
            if (user.relationshipStatus.isNullOrEmpty()){
            count--;

        }
           if (user.children.isNullOrEmpty()){
            count--;

        }
           if (user.occupation.isNullOrEmpty()){
            count--;

        }
           if (user.religiousBeliefs.isNullOrEmpty()){
            count--;

        }
           if (user.hobbiesAndInterest.isNullOrEmpty()){
            count--;
        }
        Log.d("Authenticaiton token", "percentage$"+(count/items*100)+" "+count/items)

        return (count*100)/items;
    }

    companion object {

        private const val EMAIL_PATTERN = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
        private const val DATE_PATTERN = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$"
        private val USER_NAME_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]\$";
        private val NAME_PATTERN = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";

    }
}
