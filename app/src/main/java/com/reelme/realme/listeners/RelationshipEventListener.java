package com.reelme.realme.listeners;

import android.view.View;

import com.reelme.realme.model.Flight;

public interface RelationshipEventListener {
    public void bookFlight(Flight f, View view, Integer itemPosition);
}