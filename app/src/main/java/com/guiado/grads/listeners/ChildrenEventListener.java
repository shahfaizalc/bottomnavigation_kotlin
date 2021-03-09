package com.guiado.grads.listeners;

import android.view.View;

import com.guiado.grads.model.Flight;

public interface ChildrenEventListener {
    public void bookFlight(Flight f, View view, Integer itemPosition);
}
