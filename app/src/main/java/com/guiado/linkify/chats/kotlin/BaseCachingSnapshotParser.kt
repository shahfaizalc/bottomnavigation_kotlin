package com.guiado.linkify.chats.kotlin

import android.util.LruCache
import androidx.annotation.RestrictTo

/**
 * Implementation of [BaseSnapshotParser] that caches results, so parsing a snapshot
 * repeatedly is not expensive.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
abstract class BaseCachingSnapshotParser<S, T>(private val mParser: BaseSnapshotParser<S, T>) : BaseSnapshotParser<S, T> {
    private val mObjectCache = LruCache<String, T>(MAX_CACHE_SIZE)
    /**
     * Get a unique identifier for a snapshot, should not depend on snapshot content.
     */
    abstract fun getId(snapshot: S): String

    override fun parseSnapshot(snapshot: S): T {
        val id = getId(snapshot)
        var result = mObjectCache[id]
        if (result == null) {
            val `object` = mParser.parseSnapshot(snapshot)
            mObjectCache.put(id, `object`)
            result = `object`
        }
        return result
    }

    /**
     * Clear all data in the cache.
     */
    fun clear() {
        mObjectCache.evictAll()
    }

    /**
     * Invalidate the cache for a certain document.
     */
    fun invalidate(snapshot: S) {
        mObjectCache.remove(getId(snapshot))
    }

    companion object {
        private const val MAX_CACHE_SIZE = 100
    }

}