package com.reelme.realme.listeners;

import android.view.View;

import com.reelme.realme.model.Flight;

public interface ChildrenEventListener {
    public void bookFlight(Flight f, View view, Integer itemPosition);
}
