package com.reelme.app.listeners;

import android.view.View;

import com.reelme.app.model.Flight;

public interface ChildrenEventListener {
    public void bookFlight(Flight f, View view, Integer itemPosition);
}
