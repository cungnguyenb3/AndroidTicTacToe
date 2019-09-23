package com.example.androidtictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button[][] buttons = new Button[3][3];
    TextView textViewPlayer1, textViewPlayer2, textViewRound;
    Button buttonReset, buttonHistory;
    boolean player1Turn = true;
    int count = 0, player1Points = 0, player2Points = 0, round = 0;
    String player1RoundWin = "Winner history player1: ";
    String player2RoundWin = "Winner history player2: ";
    String scoreDraw = "Draw: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        textViewRound = findViewById(R.id.text_view_round);
        buttonReset = findViewById(R.id.button_reset);
        buttonReset = findViewById(R.id.button_reset);
        buttonHistory = findViewById(R.id.button_history);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageHistory = player1RoundWin + "\n \n" + player2RoundWin + "\n \n" + scoreDraw;
                showDialog("Winner History", messageHistory);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            ((Button) view).setText("X");
        } else {
            ((Button) view).setText("0");
        }
        count++;
        if (checkForWin()) {
            if (player1Turn) {
                player1Win();
                round++;
                updateHistory(0);
            } else {
                player2Win();
                round++;
                updateHistory(1);
            }
        } else if (count == 9) {
            draw();
            round++;
            updateHistory(2);
        } else {
            player1Turn = !player1Turn;
        }
    }

    boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    void player1Win() {
        showDialog("Result", "Player 1 Win");
        player1Points++;
        resetBoard();
        updatePointText();

    }

    void player2Win() {
        showDialog("Result", "Player 2 Win");
        player2Points++;
        resetBoard();
        updatePointText();
    }

    void showDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    void updateHistory(int check) {
        textViewRound.setText("Round " + (round + 1));
        if (check == 0) {
            player1RoundWin = player1RoundWin + "round " + round + "; ";
        }
        if (check == 1) {
            player2RoundWin = player2RoundWin + "round " + round + "; ";
        }
        if (check == 2) {
            scoreDraw = scoreDraw + "round " + round + "; ";
        }
    }

    void draw() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Result");
        alertDialog.setMessage("Draw!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        resetBoard();
    }

    void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        count = 0;
        player1Turn = true;
    }

    void updatePointText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    void resetGame() {
        player1Points = 0;
        player2Points = 0;
        round = 0;
        textViewRound.setText("Round 1");
        player1RoundWin = "Winner history player1: ";
        player2RoundWin = "Winner history player2: ";
        updatePointText();
        resetBoard();
    }
}