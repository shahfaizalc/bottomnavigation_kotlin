package com.reelme.app.listeners;

import android.view.View;

import com.reelme.app.model.Flight;
import com.reelme.app.pojos.ReligiousBelief;

public interface RelegiousEventListener {
    public void bookFlight(ReligiousBelief f, View view, Integer itemPosition);
}
