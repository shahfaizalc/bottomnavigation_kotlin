package com.nioneer.nioneer.model2

import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import com.nioneer.nioneer.chats.kotlin.AbstractChat
import java.util.*

@IgnoreExtraProperties
class Chat : AbstractChat {

    private var mUid: String? = null
    private var mname: String? = null
    private var mmessage: String? = null

    @get:ServerTimestamp
    var timestamp: Date? = null

    constructor() { // Needed for Firebase
    }

    constructor(name: String?, message: String?, uid: String) {
        mname = name
        mmessage = message
        mUid = uid
    }

    override var name: String?
        get() = mname!!
        set(name) {
            mname = name
        }

    override var message: String?
        get() = mmessage!!
        set(message) {
            mmessage = message
        }

    override var uid: String
        get() = mUid!!
        set(uid) {
            mUid = uid
        }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val chat = o as Chat
        return (timestamp == chat.timestamp && mUid == chat.mUid && (if (name == null) chat.name == null else name == chat.name)
                && if (message == null) chat.message == null else message == chat.message)
    }

    override fun hashCode(): Int {
        var result = if (name == null) 0 else name.hashCode()
        result = 31 * result + if (message == null) 0 else message.hashCode()
        result = 31 * result + mUid.hashCode()
        result = 31 * result + timestamp.hashCode()
        return result
    }

    override fun toString(): String {
        return "Chat{" +
                "mName='" + name + '\'' +
                ", mMessage='" + message + '\'' +
                ", mUid='" + mUid + '\'' +
                ", mTimestamp=" + timestamp +
                '}'
    }
}