package com.reelme.app.listeners;

import android.view.View;

import com.reelme.app.model.Flight;
import com.reelme.app.pojos.RelationshipStatus;

public interface RelationshipEventListener {
    public void bookFlight(RelationshipStatus f, View view, Integer itemPosition);
}
