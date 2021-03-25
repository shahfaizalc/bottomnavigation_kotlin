package com.reelme.app.utils

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.reelme.app.listeners.StringResultListener
import java.util.regex.Matcher
import java.util.regex.Pattern

class OTP_Receiver : BroadcastReceiver() {

    fun setEditText(editText: StringResultListener) {
        Companion.editText = editText
    }

    // OnReceive will keep trace when sms is been received in mobile
    override fun onReceive(context: Context, intent: Intent) {
        //message will be holding complete sms that is received
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        for (sms in messages) {
            val msg = sms.messageBody
            // here we are spliting the sms using " : " symbol
           // val otp = msg.split(": ".toRegex()).toTypedArray()[1]
            getOtpFromMessage(msg)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var editText: StringResultListener? = null
    }

    private fun getOtpFromMessage(message: String) {
        // This will match any 6 digit number in the message
        val pattern: Pattern = Pattern.compile("(|^)\\d{6}")
        val matcher: Matcher = pattern.matcher(message)
        if (matcher.find()) {
            // otpText.setText(matcher.group(0))
                Log.d("smssms",""+matcher.group(0));
            editText!!.onSuccess( matcher.group(0));
        }
    }
}