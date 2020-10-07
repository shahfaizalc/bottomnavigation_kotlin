package com.nioneer.nioneer.chats.kotlin

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException

/**
 * Listener for changes to a [].
 */
interface ChangeEventListener : BaseChangeEventListener<DocumentSnapshot?, FirebaseFirestoreException?>