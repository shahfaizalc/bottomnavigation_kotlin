package com.reelme.realme.chats.kotlin

import com.reelme.realme.chats.BaseObservableSnapshotArray
import com.reelme.realme.chats.CachingSnapshotParser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException

/**
 * Subclass of [BaseObservableSnapshotArray] for Firestore data.
 */
abstract class ObservableSnapshotArray<T>
/**
 * @see BaseObservableSnapshotArray.BaseObservableSnapshotArray
 */(parser: SnapshotParser<T>) : BaseObservableSnapshotArray<DocumentSnapshot?, FirebaseFirestoreException?, ChangeEventListener?, T>(CachingSnapshotParser<T>(parser))