package com.nioneer.nioneer.chats;

import com.nioneer.nioneer.chats.kotlin.BaseCachingSnapshotParser;
import com.nioneer.nioneer.chats.kotlin.BaseSnapshotParser;
import com.nioneer.nioneer.chats.kotlin.SnapshotParser;
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