package com.gautambaghel.sudoku;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    // MUSIC STUFF
    MediaPlayer mMediaPlayer;
    boolean mute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // MUSIC STUFF
        mMediaPlayer = MediaPlayer.create(this, R.raw.a_guy_1_epicbuilduploop);
        mMediaPlayer.setVolume(0.5f, 0.5f);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }

    }

    public void muteVolume() {

        if (mute) {
            mute = false;
            mMediaPlayer.setVolume(0.5f, 0.5f);
        } else {
            mute = true;
            mMediaPlayer.setVolume(0, 0);
        }

    }

    private void handleViews() {
        Button bSinglePlayer = (Button) findViewById(R.id.bSinglePlayer);
        Button bExit = (Button) findViewById(R.id.bExit);

        bSinglePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SinglePlayerMatch.class);
                startActivity(intent);
            }
        });

        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
