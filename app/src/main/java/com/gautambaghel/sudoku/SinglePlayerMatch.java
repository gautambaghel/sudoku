package com.gautambaghel.sudoku;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

/*
 * Created by Gautam on 10/11/17.
 */

public class SinglePlayerMatch extends Activity {

    SinglePlayerFragment mGameFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_singleplayermatch);

        mGameFragment = (SinglePlayerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_singleplayer);
    }

    /*
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        String key = KeyEvent.keyCodeToString(keyCode);
        key = key.substring(key.length() - 1, key.length());
        // key will be something like "KEYCODE_A" - extract the "A"
        if (isNotValidKey(key)) {
            // let the default implementation handle the event
            closeKeyBoard();
            return super.onKeyUp(keyCode, event);
        } else {
            mGameFragment.pressedThis(key);
            closeKeyBoard();
            return true;
        }
    }
*/
    private void closeKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private boolean isNotValidKey(String key) {
        return !(key.equalsIgnoreCase("1") ||
                key.equalsIgnoreCase("2") ||
                key.equalsIgnoreCase("3") ||
                key.equalsIgnoreCase("4") ||
                key.equalsIgnoreCase("5") ||
                key.equalsIgnoreCase("6") ||
                key.equalsIgnoreCase("7") ||
                key.equalsIgnoreCase("8") ||
                key.equalsIgnoreCase("9"));
    }

}
