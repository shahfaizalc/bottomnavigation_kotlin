package com.guiado.grads.chats.kotlin

import com.guiado.grads.chats.BaseObservableSnapshotArray
import com.guiado.grads.chats.CachingSnapshotParser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException

/**
 * Subclass of [BaseObservableSnapshotArray] for Firestore data.
 */
abstract class ObservableSnapshotArray<T>
/**
 * @see BaseObservableSnapshotArray.BaseObservableSnapshotArray
 */(parser: SnapshotParser<T>) : BaseObservableSnapshotArray<DocumentSnapshot?, FirebaseFirestoreException?, ChangeEventListener?, T>(CachingSnapshotParser<T>(parser))