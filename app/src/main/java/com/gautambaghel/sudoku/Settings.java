package com.gautambaghel.sudoku;

/*
 * Created by Gautam on 10/27/17.
 */

class Settings {

    private boolean music;
    private String level;
    final String[] GAME_LEVELS = {"EASY", "MEDIUM", "HARD"};
    final String SETTINGS_DATA = "settings_data";

    Settings() {
        music = true;
        level = "EASY";
    }

    boolean isMusic() {
        return music;
    }

    void setMusic(boolean music) {
        this.music = music;
    }

    String getLevel() {
        return level;
    }

    void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return Boolean.toString(music) + "," + level;
    }

    void applySettings(String settings) {
        String[] s = settings.split(",");

        music = Boolean.parseBoolean(s[0]);
        level = s[1];
    }
}
