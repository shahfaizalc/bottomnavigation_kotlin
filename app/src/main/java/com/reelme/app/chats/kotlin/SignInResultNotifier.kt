package com.reelme.app.chats.kotlin

import android.content.Context
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

/**
 * Notifies the user of sign in successes or failures beyond the lifecycle of an activity.
 */
class SignInResultNotifier(context: Context) : OnCompleteListener<AuthResult?> {
    private val mContext: Context
    override fun onComplete(task: Task<AuthResult?>) {
        if (task.isSuccessful) {
            Toast.makeText(mContext, "signed_in", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mContext, "anonymous_auth_failed_msg", Toast.LENGTH_LONG).show()
        }
    }

    init {
        mContext = context.applicationContext
    }
}