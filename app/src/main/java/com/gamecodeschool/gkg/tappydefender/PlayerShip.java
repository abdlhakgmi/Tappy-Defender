package com.gamecodeschool.gkg.tappydefender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created on 9/22/2015.
 */
public class PlayerShip {

    private final int Gravity = -12;

    // Stop ship leaving the screen
    private int maxY;
    private int minY;

    // Limit the bounds of the ship's speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private Bitmap bitmap;
    private int x, y;
    private int speed = 0;
    private boolean boosting;

    public PlayerShip(Context context, int screenX, int screenY){
        x = 50;
        y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        boosting = false;
        maxY = screenY - bitmap.getHeight();
        minY = 0;
    }

    public void update() {
        // Are we boosting?
        if(boosting){
            // Speed up
            speed += 2;
        }
        else {
            // Slow down
            speed -= 5;
        }

        // Constrain top speed
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }

        // Never stop completely
        if(speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        // move the ship up or down
        y -= speed + Gravity;

        // But don't let the ship stray off screen
        if(y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }
    }

    //Getters
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setBoosting() {
        boosting = true;
    }

    public void stopBoostring(){
        boosting = false;
    }
}
