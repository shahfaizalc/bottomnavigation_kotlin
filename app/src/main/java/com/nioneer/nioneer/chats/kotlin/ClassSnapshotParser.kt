package com.nioneer.nioneer.chats.kotlin

import com.nioneer.nioneer.chats.Preconditions
import com.google.firebase.firestore.DocumentSnapshot

/**
 * An implementation of [SnapshotParser] that converts [DocumentSnapshot] to
 * a class using [DocumentSnapshot.toObject].
 */
class ClassSnapshotParser<T>(modelClass: Class<T>) : SnapshotParser<T> {
    private val mModelClass: Class<T>

    init {
        mModelClass = Preconditions.checkNotNull(modelClass)
    }

    override fun parseSnapshot(snapshot: DocumentSnapshot?): T {
        return snapshot!!.toObject(mModelClass)!!
    }
}