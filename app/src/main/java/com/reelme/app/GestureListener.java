package com.reelme.app;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class GestureListener extends GestureDetector.SimpleOnGestureListener
{
    private static final int MIN_SWIPPING_DISTANCE = 50;
    private static final int THRESHOLD_VELOCITY = 50;

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        if (e1.getX() - e2.getX() > MIN_SWIPPING_DISTANCE && Math.abs(velocityX) > THRESHOLD_VELOCITY)
        {
           Log.d( "","You have swipped left side");
            /* Code that you want to do on swiping left side*/
            return false;
        }
        else if (e2.getX() - e1.getX() > MIN_SWIPPING_DISTANCE && Math.abs(velocityX) > THRESHOLD_VELOCITY)
        {
            Log.d( "","You have swipped right side");
            /* Code that you want to do on swiping right side*/
            return false;
        }
        return false;
    }
}