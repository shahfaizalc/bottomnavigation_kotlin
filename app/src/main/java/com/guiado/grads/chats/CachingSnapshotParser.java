package com.guiado.grads.chats;

import com.guiado.grads.chats.kotlin.BaseCachingSnapshotParser;
import com.guiado.grads.chats.kotlin.BaseSnapshotParser;
import com.guiado.grads.chats.kotlin.SnapshotParser;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;

/**
 * Implementation of {@link BaseCachingSnapshotParser} for {@link DocumentSnapshot}.
 */
public class CachingSnapshotParser<T> extends BaseCachingSnapshotParser<DocumentSnapshot, T>
        implements SnapshotParser<T> {

    public CachingSnapshotParser(@NonNull BaseSnapshotParser<DocumentSnapshot, T> parser) {
        super(parser);
    }

    @NonNull
    @Override
    public String getId(@NonNull DocumentSnapshot snapshot) {
        return snapshot.getId();
    }
}