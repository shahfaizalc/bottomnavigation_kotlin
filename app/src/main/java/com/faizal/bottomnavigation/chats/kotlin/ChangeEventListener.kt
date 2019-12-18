package com.faizal.bottomnavigation.chats.kotlin

import com.faizal.bottomnavigation.chats.kotlin.BaseChangeEventListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException

/**
 * Listener for changes to a [].
 */
interface ChangeEventListener : BaseChangeEventListener<DocumentSnapshot?, FirebaseFirestoreException?>