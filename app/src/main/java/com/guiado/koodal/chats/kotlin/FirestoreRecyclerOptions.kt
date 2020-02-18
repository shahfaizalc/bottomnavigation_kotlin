package com.guiado.koodal.chats.kotlin

import androidx.lifecycle.LifecycleOwner
import com.guiado.koodal.chats.*
import com.guiado.koodal.chats.kotlin.FirestoreRecyclerOptions.Builder
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query

/**
 * Options to configure an [FirestoreRecyclerAdapter].
 *
 * @see Builder
 */
class FirestoreRecyclerOptions<T> private constructor(val snapshots: ObservableSnapshotArray<T>,
                                                      val owner: LifecycleOwner?) {
    /**
     * Get the [ObservableSnapshotArray] to observe.
     */
    /**
     * Get the (optional) [LifecycleOwner].
     */

    /**
     * Builder for [FirestoreRecyclerOptions].
     *
     * @param <T> the model class for the [FirestoreRecyclerAdapter].
    </T> */
    class Builder<T> {
        private var mSnapshots: ObservableSnapshotArray<T>? = null
        private var mOwner: LifecycleOwner? = null
        /**
         * Directly set the [ObservableSnapshotArray].
         *
         *
         * Do not call this method after calling `setQuery`.
         */
        fun setSnapshotArray(snapshots: ObservableSnapshotArray<T>): Builder<T> {
            Preconditions.assertNull(mSnapshots, ERR_SNAPSHOTS_SET)
            mSnapshots = snapshots
            return this
        }

        /**
         * Calls [.setQuery] with metadata changes excluded.
         */
        fun setQuery(query: Query, parser: SnapshotParser<T>): Builder<T> {
            return setQuery(query, MetadataChanges.EXCLUDE, parser)
        }

        /**
         * Calls [.setQuery] with metadata changes excluded.
         */
        fun setQuery(query: Query, modelClass: Class<T>): Builder<T> {
            return setQuery(query, MetadataChanges.EXCLUDE, modelClass)
        }

        /**
         * Set the query to use (with options) and provide a model class to which each snapshot will
         * be converted.
         *
         *
         * Do not call this method after calling [.setSnapshotArray].
         */
        fun setQuery(query: Query,
                     changes: MetadataChanges,
                     modelClass: Class<T>): Builder<T> {
            return setQuery(query, changes, ClassSnapshotParser(modelClass))
        }

        /**
         * Set the query to use (with options) and provide a custom [SnapshotParser].
         *
         *
         * Do not call this method after calling [.setSnapshotArray].
         */
        fun setQuery(query: Query,
                     changes: MetadataChanges,
                     parser: SnapshotParser<T>): Builder<T> {
            Preconditions.assertNull(mSnapshots, ERR_SNAPSHOTS_SET)
            mSnapshots = FirestoreArray(query, changes, parser)
            return this
        }

        /**
         * Set a [LifecycleOwner] for the adapter. Listening will stop/start after the
         * appropriate lifecycle events.
         */
        fun setLifecycleOwner(owner: LifecycleOwner?): Builder<T> {
            mOwner = owner
            return this
        }

        /**
         * Build a [FirestoreRecyclerOptions] from the provided arguments.
         */
        fun build(): FirestoreRecyclerOptions<T> {
            Preconditions.assertNonNull(mSnapshots, ERR_SNAPSHOTS_NULL)
            return FirestoreRecyclerOptions(mSnapshots!!, mOwner)
        }
    }

    companion object {
        private const val ERR_SNAPSHOTS_SET = "Snapshot array already set. " +
                "Call only one of setSnapshotArray or setQuery"
        private const val ERR_SNAPSHOTS_NULL = "Snapshot array cannot be null. " +
                "Call one of setSnapshotArray or setQuery"
    }

}