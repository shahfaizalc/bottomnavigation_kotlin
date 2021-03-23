package com.reelme.app.listeners;

import android.view.View;

import com.reelme.app.pojos.Occupation;
import com.reelme.app.pojos.ReligiousBelief;

public interface OccEventListener {
    public void bookFlight(Occupation f, View view, Integer itemPosition);
}
