package com.faizal.guiado.chats.kotlin

/**
 * Common interface for chat messages, helps share code between RTDB and Firestore examples.
 */
abstract class AbstractChat {
    abstract var name: String?
    abstract var message: String?
    abstract var uid: String
    abstract override fun equals(obj: Any?): Boolean
    abstract override fun hashCode(): Int
}