package com.gamecodeschool.gkg.tappydefender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created on 9/21/2015.
 */

public class TDView extends SurfaceView implements Runnable
{
    volatile boolean playing;
    Thread gameThread = null;

    // Game Objects
    private PlayerShip player;
    private EnemyShip enemy1;
    private EnemyShip enemy2;
    private EnemyShip enemy3;

    // Make some random space dust
    public ArrayList<SpaceDust> dustList = new ArrayList<SpaceDust>();

    // For drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public TDView(Context context, int x, int y) {
        super(context);

        // Initialize our drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        // Initialize our player ship
        player = new PlayerShip(context, x, y);

        enemy1 = new EnemyShip(context, x, y);
        enemy2 = new EnemyShip(context, x, y);
        enemy3 = new EnemyShip(context, x, y);

        int numSpecs = 40;

        for (int i = 0; i < numSpecs; i++)
        {
            // Where will the dust spawn?
            SpaceDust spec = new SpaceDust(x, y);
            dustList.add(spec);
        }
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
        // Collision detection on new positions before moving since need to test last frames position which has just been drawn

        // If images in excess of 100 pixels wide then increase -100 accordingly
        if(Rect.intersects(player.getHitBox(), enemy1.getHitBox())){
            enemy1.setX(-100);
        }

        if(Rect.intersects(player.getHitBox(), enemy2.getHitBox())){
            enemy2.setX(-100);
        }

        if(Rect.intersects(player.getHitBox(), enemy3.getHitBox())){
            enemy3.setX(-100);
        }

        // Update the player
        player.update();

        // Update the enemies
        enemy1.update(player.getSpeed());
        enemy2.update(player.getSpeed());
        enemy3.update(player.getSpeed());

        for(SpaceDust sd: dustList)
        {
            sd.update(player.getSpeed());
        }
    }

    private void draw(){
        if(ourHolder.getSurface().isValid()) {
            // First we lock the area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();

            // Rub out the last frame
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            // For debugging
            // Switch to white pixels
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Draw the hit boxes
            canvas.drawRect(
                player.getHitBox().left,
                player.getHitBox().top,
                player.getHitBox().right,
                player.getHitBox().bottom,
                paint
            );

            canvas.drawRect(
                enemy1.getHitBox().left,
                enemy1.getHitBox().top,
                enemy1.getHitBox().right,
                enemy1.getHitBox().bottom,
                paint
            );

            canvas.drawRect(
                enemy2.getHitBox().left,
                enemy2.getHitBox().top,
                enemy2.getHitBox().right,
                enemy2.getHitBox().bottom,
                paint
            );

            canvas.drawRect(
                enemy3.getHitBox().left,
                enemy3.getHitBox().top,
                enemy3.getHitBox().right,
                enemy3.getHitBox().bottom,
                paint
            );

            // White specs of dust
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Draw the dust from the arrayList
            for (SpaceDust sd: dustList) {
                canvas.drawPoint(sd.getX(), sd.getY(), paint);
            }

            // Draw the player
            canvas.drawBitmap(
                player.getBitmap(),
                player.getX(),
                player.getY(),
                paint
            );

            canvas.drawBitmap(
                enemy1.getBitmap(),
                enemy1.getX(),
                enemy1.getY(),
                paint
            );

            canvas.drawBitmap(
                enemy2.getBitmap(),
                enemy2.getX(),
                enemy2.getY(),
                paint
            );

            canvas.drawBitmap(
                    enemy3.getBitmap(),
                    enemy3.getX(),
                    enemy3.getY(),
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

    // SurfaceView allows us to handle the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            // Has the player lifted their finger up?
            case  MotionEvent.ACTION_UP:
                player.stopBoostring();
                break;
            // Has the player touched the screen?
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
        }
        return true;
    }
}
