package com.gamecodeschool.gkg.tappydefender;

import android.app.Activity;
import android.os.Bundle;


public class GameActivity extends Activity {

    // Object to the handle the View
    private TDView gameView;

    // This is where the "Play" button from HomeActivity sends us
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create an instance of the Tappy Defender View (TDView)
        // Also passing in "this" which is the Context of the app
        gameView = new TDView(this);

        // Make the gameView the view for the Activity
        setContentView(gameView);
    }

    // If the Activity is paused make sure to pause the thread
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    // If the Activity is resumed, make sure to resume the thread
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}
