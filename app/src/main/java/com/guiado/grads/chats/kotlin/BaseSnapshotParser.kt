package com.guiado.grads.chats.kotlin

/**
 * Common interface for snapshot parsers.
 *
 * @param <S> snapshot type.
 * @param <T> parsed object type.
</T></S> */
interface BaseSnapshotParser<S, T> {
    /**
     * This method parses the Snapshot into the requested type.
     *
     * @param snapshot the Snapshot to extract the model from
     * @return the model extracted from the DataSnapshot
     */
    fun parseSnapshot(snapshot: S): T
}