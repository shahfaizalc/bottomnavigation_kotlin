package com.faizal.bottomnavigation.chats

import com.faizal.bottomnavigation.chats.kotlin.ChangeEventType
import com.faizal.bottomnavigation.chats.kotlin.ObservableSnapshotArray
import com.faizal.bottomnavigation.chats.kotlin.SnapshotParser
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.util.*

/**
 * Exposes a Firestore query as an observable list of objects.
 */
class FirestoreArray<T>
/**
 * @param changes metadata options for the query listen.
 * @see .FirestoreArray
 */(private val mQuery: Query,
    private val mMetadataChanges: MetadataChanges,
    parser: SnapshotParser<T>) : ObservableSnapshotArray<T>(parser), EventListener<QuerySnapshot?> {
    private var mRegistration: ListenerRegistration? = null
    private val mSnapshots: MutableList<DocumentSnapshot?> = ArrayList()

    /**
     * Create a new FirestoreArray.
     *
     * @param query  query to listen to.
     * @param parser parser for DocumentSnapshots.
     * @see ObservableSnapshotArray.ObservableSnapshotArray
     */
    constructor(query: Query, parser: SnapshotParser<T>) : this(query, MetadataChanges.EXCLUDE, parser) {}

    override fun getSnapshots(): List<DocumentSnapshot?> {
        return mSnapshots
    }

    override fun onCreate() {
        super.onCreate()
        mRegistration = mQuery.addSnapshotListener(mMetadataChanges, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRegistration!!.remove()
        mRegistration = null
    }

    override fun onEvent(snapshots: QuerySnapshot?, e: FirebaseFirestoreException?) {
        if (e != null) {
            notifyOnError(e)
            return
        }
        // Break down each document event
        val changes = snapshots!!.getDocumentChanges(mMetadataChanges)
        for (change in changes) {
            when (change.type) {
                DocumentChange.Type.ADDED -> onDocumentAdded(change)
                DocumentChange.Type.REMOVED -> onDocumentRemoved(change)
                DocumentChange.Type.MODIFIED -> onDocumentModified(change)
            }
        }
        notifyOnDataChanged()
    }

    private fun onDocumentAdded(change: DocumentChange) {
        val snapshot = change.document
        mSnapshots.add(change.newIndex, snapshot)
        notifyOnChildChanged(ChangeEventType.ADDED, snapshot, change.newIndex, -1)
    }

    private fun onDocumentRemoved(change: DocumentChange) {
        mSnapshots.removeAt(change.oldIndex)
        val snapshot = change.document
        notifyOnChildChanged(ChangeEventType.REMOVED, snapshot, -1, change.oldIndex)
    }

    private fun onDocumentModified(change: DocumentChange) {
        val snapshot = change.document
        if (change.oldIndex == change.newIndex) { // Document modified only
            mSnapshots[change.newIndex] = snapshot
            notifyOnChildChanged(ChangeEventType.CHANGED, snapshot,
                    change.newIndex, change.newIndex)
        } else { // Document moved and possibly also modified
            mSnapshots.removeAt(change.oldIndex)
            mSnapshots.add(change.newIndex, snapshot)
            notifyOnChildChanged(ChangeEventType.MOVED, snapshot,
                    change.newIndex, change.oldIndex)
            notifyOnChildChanged(ChangeEventType.CHANGED, snapshot,
                    change.newIndex, change.newIndex)
        }
    }

}