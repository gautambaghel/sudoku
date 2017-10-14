package com.gautambaghel.sudoku;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import static android.graphics.Typeface.BOLD;

/*
 * Created by Gautam on 10/11/17.
 */

public class SinglePlayerFragment extends Fragment {


    private Tile mEntireBoard = new Tile(this);
    private Tile mLargeTiles[] = new Tile[9];
    private Tile mSmallTiles[][] = new Tile[9][9];

    static private int mLargeIds[] = {R.id.large1, R.id.large2, R.id.large3,
            R.id.large4, R.id.large5, R.id.large6, R.id.large7, R.id.large8,
            R.id.large9,};

    static private int mSmallIds[] = {R.id.small1, R.id.small2, R.id.small3,
            R.id.small4, R.id.small5, R.id.small6, R.id.small7, R.id.small8,
            R.id.small9,};

    static private int mTvIds[] = {R.id.tvSmall1, R.id.tvSmall2, R.id.tvSmall3,
            R.id.tvSmall4, R.id.tvSmall5, R.id.tvSmall6, R.id.tvSmall7, R.id.tvSmall8,
            R.id.tvSmall9,};

    private TextWatcher textWatcher = new TextWatcher() {

        boolean fromLeft;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            fromLeft = start == 0 && !cleanKey(s.toString()).equalsIgnoreCase("");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            try {
                if (fromLeft) {
                    s.delete(1, s.length());
                } else {
                    s.delete(0, s.length() - 1);
                }
            } catch (Exception ignored) {
            }

            if (isInvalidKey(cleanKey(s.toString())))
                s.clear();
            hideKeyBoard();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initGame();
    }

    private void initGame() {
        Log.d("UT3", "init game");

        SudokuGenerator sg = new SudokuGenerator();
        sg.nextBoard(35);
        sg.print();

        mEntireBoard = new Tile(this);

        // Create all the tiles
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large] = new Tile(this);

            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small] = new Tile(this);

                int number = sg.getThisElementOnBoard(large, small);
                if (number == 0) {
                    mSmallTiles[large][small].setState(Tile.State.VARIABLE);
                } else {
                    mSmallTiles[large][small].setState(Tile.State.FIXED);
                }
                mSmallTiles[large][small].setNumber(number);
            }
            mLargeTiles[large].setSubTiles(mSmallTiles[large]);
        }
        mEntireBoard.setSubTiles(mLargeTiles);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.largeboard, container, false);
        initViews(rootView);
        updateAllTiles();
        return rootView;
    }

    private void initViews(View rootView) {

        mEntireBoard.setView(rootView);
        for (int large = 0; large < 9; large++) {
            View outer = rootView.findViewById(mLargeIds[large]);
            mLargeTiles[large].setView(outer);

            for (int small = 0; small < 9; small++) {
                ImageButton inner = (ImageButton) outer.findViewById(mSmallIds[small]);
                final EditText etInner = (EditText) outer.findViewById(mTvIds[small]);

                String number = "";
                final Tile smallTile = mSmallTiles[large][small];
                if (smallTile.getNumber() != 0)
                    number = "" + smallTile.getNumber();

                etInner.setRawInputType(Configuration.KEYBOARD_12KEY);
                etInner.setText(number.toUpperCase());
                etInner.clearFocus();

                if (smallTile.getState() == Tile.State.FIXED) {
                    etInner.setTextColor(Color.BLACK);
                    etInner.setTypeface(null, BOLD);
                    etInner.setFocusable(false);
                }

                smallTile.setView(inner);
                etInner.addTextChangedListener(textWatcher);
            }
        }
    }

    private void hideKeyBoard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private String cleanKey(String s) {
        return s.replaceAll("[^0-9]", "");
    }

    private boolean isInvalidKey(String key) {
        return !key.matches("[1-9]");
    }

    private void updateAllTiles() {
        mEntireBoard.updateDrawableState();
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large].updateDrawableState();
            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small].updateDrawableState();
            }
        }
    }

    public int[][] getBoard() {
        int[][] board = new int[9][9];
        for (int large = 0; large < 9; large++)
            for (int small = 0; small < 9; small++)
                board[large][small] = mSmallTiles[large][small].getNumber();
        return board;
    }

    public void resetBoard() {
        initGame();
        initViews(getView());
        updateAllTiles();
    }

}
