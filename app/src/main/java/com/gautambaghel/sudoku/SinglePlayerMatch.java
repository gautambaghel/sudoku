package com.gautambaghel.sudoku;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.app.AlertDialog;

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

        ImageButton submit = (ImageButton) findViewById(R.id.bSubmit);
        ImageButton retry = (ImageButton) findViewById(R.id.bRetry);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                boolean won = false;

                int[][] gameBoard = mGameFragment.getBoard();
                SudokuSolver ss = new SudokuSolver(gameBoard);

                if (!ss.completed())
                    message = "Sudoku board is unfinished";
                else if (!ss.checkPuzzle())
                    message = "Sudoku board not solved correctly";
                else {
                    message = "You've solved this board, YAY!";
                    won = true;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(SinglePlayerMatch.this);
                builder.setCancelable(true);
                builder.setMessage(message);
                builder.show();

                if (won)
                   onBackPressed();
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mGameFragment.resetBoard();
            }
        });
    }

}
