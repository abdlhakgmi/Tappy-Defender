package com.gamecodeschool.gkg.tappydefender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created on 9/21/2015.
 */

public class TDView extends SurfaceView implements Runnable
{
    volatile boolean playing;
    Thread gameThread = null;

    // Game Objects
    private PlayerShip player;

    // For drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public TDView(Context context) {
        super(context);

        // Initialize our drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        // Initialize our player ship
        player = new PlayerShip(context);
    }

    @Override
    public void run(){
        while (playing){
            update();
            draw();
            control();
        }
    }

    private void update(){
        // Update the player
        player.update();
    }

    private void draw(){
        if(ourHolder.getSurface().isValid()) {
            // First we lock the area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();

            // Rub out the last frame
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            // Draw the playere
            canvas.drawBitmap(
                player.getBitmap(),
                player.getX(),
                player.getY(),
                paint
            );

            // Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){
        try {
            // pause thread for 17 ms
            gameThread.sleep(17);
        }
        catch (InterruptedException e) {
            // do nothing
        }
    }

    // Clean up our thread if the game is interrupted or the player quits
    public void pause(){
        playing = false;
        try {
            gameThread.join();
        }
        catch (InterruptedException e) {
            // do something
        }
    }

    // Make a new thread and start it
    // Execution moves to the R
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
