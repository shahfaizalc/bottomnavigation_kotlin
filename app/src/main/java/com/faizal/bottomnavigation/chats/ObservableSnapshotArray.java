package com.faizal.bottomnavigation.chats;

import com.faizal.bottomnavigation.chats.kotlin.BaseCachingSnapshotParser;
import com.faizal.bottomnavigation.chats.kotlin.ChangeEventListener;
import com.faizal.bottomnavigation.chats.kotlin.SnapshotParser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.NonNull;

/**
 * Subclass of {@link BaseObservableSnapshotArray} for Firestore data.
 */
public abstract class ObservableSnapshotArray<T>
        extends BaseObservableSnapshotArray<DocumentSnapshot, FirebaseFirestoreException, ChangeEventListener, T> {
    /**
     * @see BaseObservableSnapshotArray#BaseObservableSnapshotArray(BaseCachingSnapshotParser)
     */
    public ObservableSnapshotArray(@NonNull SnapshotParser<T> parser) {
        super(new CachingSnapshotParser<>(parser));
    }
}
