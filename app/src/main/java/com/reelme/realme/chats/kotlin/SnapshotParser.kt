package com.reelme.realme.chats.kotlin

import com.google.firebase.firestore.DocumentSnapshot

/**
 * Base interface for a [BaseSnapshotParser] for [DocumentSnapshot].
 */
interface SnapshotParser<T> : BaseSnapshotParser<DocumentSnapshot?, T>