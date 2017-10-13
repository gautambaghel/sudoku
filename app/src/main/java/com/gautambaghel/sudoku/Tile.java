package com.gautambaghel.sudoku;

/*
 * Created by Gautam on 10/11/17.
 */

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.app.Fragment;

public class Tile {

    public Tile(Fragment fragment) {
        this.context = fragment.getActivity();
    }

    public char getNumber() {
        return number;
    }

    public enum State {
        FIXED, VARIABLE
    }

    private final Context context;

    private static final int FIXED = 0;
    private static final int VARIABLE = 1;

    private State mState = State.VARIABLE;
    private View mView;
    private Tile mSubTiles[];
    private char number;

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        this.mView = view;
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        this.mState = state;
    }

    public void setSubTiles(Tile[] subTiles) {
        this.mSubTiles = subTiles;
    }

    public void setNumber(char number) {
        this.number = number;
    }

    public void updateDrawableState() {
        if (mView == null) return;
        int level = getLevel();
        if (mView.getBackground() != null) {
            mView.getBackground().setLevel(level);
        }
        if (mView instanceof ImageButton) {
            Drawable drawable = ((ImageButton) mView).getDrawable();
            drawable.setLevel(level);
        }
        if (mView instanceof TextView) {
            String al = "" + number;
            ((TextView) mView).setText(al.toUpperCase());
        }
    }

    private int getLevel() {

        int level = VARIABLE;
        switch (mState) {
            case FIXED:
                level = FIXED;
                break;
            case VARIABLE:
                level = VARIABLE;
                break;
        }
        return level;
    }

    public void animate() {
        Animator anim = AnimatorInflater.loadAnimator(context, R.animator.sudoku);
        if (getView() != null) {
            anim.setTarget(getView());
            anim.start();
        }
    }
}