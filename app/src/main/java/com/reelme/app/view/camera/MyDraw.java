package com.reelme.app.view.camera;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.reelme.app.view.RecordActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MyDraw extends View {
    private RecordActivity ma;
    private int x, y, radius, h, w;
    private boolean pause = true, isFirst;
    private int time;
    private List<List<Integer>> points;

    public void setMa(RecordActivity ma) {
        this.ma = ma;
    }

    public MyDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        isFirst = true;
        points = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        if (isFirst) {
            fillAll(c);
        }
        if (!pause) {
            if (time == 80) {
                time = 0;
                newpoint();
            }

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            c.drawColor(Color.BLACK);
            c.drawCircle(x, y, radius, paint);
            points.add(Arrays.asList(x, y, time));
            time++;


        }
        invalidate();

    }

    protected void fillAll(Canvas c) {
        isFirst = false;
        time = 0;
        h = c.getHeight();
        w = c.getWidth();
        radius = w / 20;
        x = radius;
        y = radius;
    }

    protected void newpoint() {
        int newX, newY;
        do {
            newX = randint(radius, w - radius);
            newY = randint(radius, h - radius * 2);
        }
        while (Math.sqrt(Math.pow(x - newX, 2) + Math.pow(y - newY, 2)) < (h / 10));

        x = newX;
        y = newY;
    }

    protected int randint(int a, int b) {
        return (int) (Math.random() * (b - a + 1) + a);
    }


    public void setPause(boolean b) {
        pause = b;
    }
}
