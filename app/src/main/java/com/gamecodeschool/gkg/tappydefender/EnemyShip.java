package com.gamecodeschool.gkg.tappydefender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created on 9/28/2015.
 */
public class EnemyShip {
    private Bitmap bitmap;
    private int x, y;
    private int speed = 1;

    // Detect enemies leaving the screen
    private int maxX;
    private int minX;

    // Spawn enemies within screen bounds
    private int maxY;
    private int minY;


    // A hit box for collision detection
    private Rect hitBox;

    public EnemyShip(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);

        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        Random generator = new Random();
        speed = generator.nextInt(6) + 10;

        x = screenX;
        y = generator.nextInt(maxY) - bitmap.getHeight();

        // Initialize the hit box
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rect getHitBox() {
        return hitBox;
    }

   public void update(int playerSpeed) {
       // Move to the left
       x -= playerSpeed;
       x -= speed;

       // respawn when off screen
       if(x < minX-bitmap.getWidth()) {
           Random generator = new Random();
           speed = generator.nextInt(10) + 10;
           x = maxX;
           y = generator.nextInt(maxY) - bitmap.getHeight();
       }

       // Refresh the hit box location
       hitBox.left = x;
       hitBox.top = y;
       hitBox.right = x + bitmap.getWidth();
       hitBox.bottom = y + bitmap.getHeight();
   }

    // Used by th eTDView update() methods to make an enemy out of bounds and force a re-spawn
    public void setX(int x){
        this.x = x;
    }
}
