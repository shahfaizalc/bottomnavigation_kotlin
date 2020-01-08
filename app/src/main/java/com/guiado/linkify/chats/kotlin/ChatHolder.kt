package com.guiado.linkify.chats.kotlin

import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RotateDrawable
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.guiado.linkify.R
import com.google.firebase.auth.FirebaseAuth

class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mNameField: TextView
    private val mTextField: TextView
    private val mLeftArrow: FrameLayout
    private val mRightArrow: FrameLayout
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
            mLeftArrow.visibility = View.GONE
            mRightArrow.visibility = View.VISIBLE
            mMessageContainer.gravity = Gravity.END
            mMessage.gravity = Gravity.END

        } else {
            mMessage.gravity = Gravity.START
            color = mGray300
            mLeftArrow.visibility = View.VISIBLE
            mRightArrow.visibility = View.GONE
            mMessageContainer.gravity = Gravity.START
        }
        (mMessage.background as GradientDrawable).setColor(color)
        (mLeftArrow.background as RotateDrawable).drawable
                ?.setColorFilter(color, PorterDuff.Mode.SRC)
        (mRightArrow.background as RotateDrawable).drawable
                ?.setColorFilter(color, PorterDuff.Mode.SRC)
    }

    init {
        mNameField = itemView.findViewById(R.id.name_text)
        mTextField = itemView.findViewById(R.id.message_text)
        mLeftArrow = itemView.findViewById(R.id.left_arrow)
        mRightArrow = itemView.findViewById(R.id.right_arrow)
        mMessageContainer = itemView.findViewById(R.id.message_container)
        mMessage = itemView.findViewById(R.id.message)
        mGreen300 = ContextCompat.getColor(itemView.context, R.color.green2)
        mGray300 = ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark)
    }
}