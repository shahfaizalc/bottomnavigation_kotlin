package com.reelme.app.chats.kotlin

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.reelme.app.R
import com.google.firebase.auth.FirebaseAuth

class DiscussionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mNameField: TextView
    private val mTextField: TextView
    private val mMessageContainer: RelativeLayout
    private val mMessage: LinearLayout
    private val mGreen300: Int
    private val mGray300: Int
    fun bind(chat: AbstractChat) {
        setName(chat.name)
        setMessage(chat.message)
        val currentUser = FirebaseAuth.getInstance().currentUser
        setIsSender(currentUser != null && chat.uid == currentUser.uid)
    }

    private fun setName(name: String?) {
        mNameField.text = name
    }

    private fun setMessage(text: String?) {
        mTextField.text = text
    }

    private fun setIsSender(isSender: Boolean) {
        val color: Int
        if (isSender) {
            color = mGreen300

        } else {
            color = mGray300
        }
        (mMessage.background as GradientDrawable).setColor(color)
        (mMessage.background as GradientDrawable).alpha = 80

    }

    init {
        mNameField = itemView.findViewById(R.id.name_text)
        mTextField = itemView.findViewById(R.id.message_text)
        mMessageContainer = itemView.findViewById(R.id.message_container)
        mMessage = itemView.findViewById(R.id.message)
        mGreen300 = ContextCompat.getColor(itemView.context, R.color.white)
        mGray300 = ContextCompat.getColor(itemView.context, R.color.white)
    }
}