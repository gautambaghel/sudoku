package com.gautambaghel.sudoku;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {


    // MUSIC STUFF
    private MediaPlayer mMediaPlayer;
    private boolean mute;

    static Settings settings = new Settings();
    private AlertDialog dSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleViews();

        String settingsData = this.getPreferences(MODE_PRIVATE)
                .getString(settings.SETTINGS_DATA, null);
        if (settingsData != null)
            settings.applySettings(settingsData);
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
        saveSettings();
        super.onPause();

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }

    }

    private void saveSettings() {
        String settingsData = settings.toString();
        getPreferences(MODE_PRIVATE).edit()
                .putString(settings.SETTINGS_DATA, settingsData)
                .apply();
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
        Button bSettings = (Button) findViewById(R.id.bSettings);

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

        bSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder_help = new Builder(MainActivity.this);
                LayoutInflater li = MainActivity.this.getLayoutInflater();
                View promptsView = li.inflate(R.layout.dialog_settings, null);
                builder_help.setView(promptsView);

                final Switch musicSwitch = (Switch) promptsView.findViewById(R.id.switchMusic);
                Spinner levelSpinner = (Spinner) promptsView.findViewById(R.id.levelSpinner);
                Button bSaveSettings = (Button) promptsView.findViewById(R.id.bSaveSettings);

                initSwitch(musicSwitch);
                initLevelSpinner(levelSpinner);
                musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        settings.setMusic(musicSwitch.isChecked());
                    }
                });
                bSaveSettings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dSettings != null)
                            dSettings.dismiss();
                    }
                });

                dSettings = builder_help.create();
                dSettings.show();
            }
        });
    }

    private void initSwitch(Switch musicSwitch) {
        musicSwitch.setChecked(settings.isMusic());
    }

    private void initLevelSpinner(Spinner levelSpinner) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(params);
                return view;
            }
        };

        String[] levels = settings.GAME_LEVELS;
        String selectedLevel = settings.getLevel();

        // Fill the spinner with available languages selecting the previously chosen language
        int selectedIndex = -1;
        for (int i = 0; i < levels.length; i++) {
            String name = levels[i];
            adapter.add(name);
            if (name.equalsIgnoreCase(selectedLevel)) {
                selectedIndex = i;
            }
        }
        if (selectedIndex == -1) {
            adapter.insert(selectedLevel, 0);
            selectedIndex = 0;
        }

        levelSpinner.setAdapter(adapter);
        levelSpinner.setSelection(selectedIndex);

        // The callback to be called when a language is selected
        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String level = (String) parent.getItemAtPosition(position);
                settings.setLevel(level);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

}
