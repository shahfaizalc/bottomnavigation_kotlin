package com.guiado.akbhar.chats.kotlin

/**
 * Enumeration of change event types for children of an observable snapshot array.
 */
enum class ChangeEventType {
    /**
     * An element was added to the array.
     */
    ADDED,
    /**
     * An element in the array has new content.
     */
    CHANGED,
    /**
     * An element was removed from the array.
     */
    REMOVED,
    /**
     * An element in the array has new content, which caused a change in position.
     */
    MOVED
}