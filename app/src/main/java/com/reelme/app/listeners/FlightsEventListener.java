package com.reelme.app.listeners;

import android.view.View;

import com.reelme.app.model.Flight;
import com.reelme.app.pojos.Gender;

public interface FlightsEventListener {
    public void bookFlight(Gender f, View view, Integer itemPosition);
}
