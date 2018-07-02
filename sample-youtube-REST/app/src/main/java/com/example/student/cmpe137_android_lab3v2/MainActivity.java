package com.example.student.cmpe137_android_lab3v2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity {

    private static final int RECOVERY_REQUEST = 1;
    // My created button
    Button btn;
    // The video layout reference
    private YouTubePlayerView youTubePlayerView;
    // Input handler for youtube compressed video link format
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instance of YTPV
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        // Generates video on successful data entry
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restore) {
                if(!restore) {
                    // Dog video
                    youTubePlayer.loadVideo("SfLV8hD7zX4");
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
//                // Issue with playback, but you can recover
//                if(error.isUserRecoverableError()) {
//                    error.getErrorDialog(getParent(), RECOVERY_REQUEST).show();
//                } else {
//                    // Cannot playback, error code from Youtube API pop up
//                    String issue = error.toString();
//                    Toast.makeText(getApplicationContext(), issue, Toast.LENGTH_LONG).show();
//                }
            }
        };

        // Assign button to View button in XML
        btn = (Button) findViewById(R.id.button);
        // Assign that button to control Youtube Player
        btn.setOnClickListener(new View.OnClickListener(){
            // API Key intialization
            @Override
            public void onClick(View view) {
                youTubePlayerView.initialize("AIzaSyCuunGSBhBPOsgSVZrD4mwS8co3FkZx9OI", onInitializedListener);
            }
        });
    }
}
