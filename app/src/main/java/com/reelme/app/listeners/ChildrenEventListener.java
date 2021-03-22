package com.reelme.app.listeners;

import android.view.View;

import com.reelme.app.model.Flight;
import com.reelme.app.pojos.Child;

public interface ChildrenEventListener {
    public void bookFlight(Child f, View view, Integer itemPosition);
}
