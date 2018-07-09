package com.yongcheng.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    int current_player = 0;
    int[] game_state = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winning_positions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    boolean game_over = false;
    String winner = "";

    public void make_move(View view)
    {
        ImageView move = (ImageView) view;
        int current_position = Integer.parseInt(view.getTag().toString());

        if (game_state[current_position] == 2 && game_over == false)
        {
            // Update game state.
            game_state[current_position] = current_player;

            if (current_player == 0)
            {
                move.setImageResource(R.drawable.x);
                current_player = 1;
            }
            else
            {
                move.setImageResource(R.drawable.o);
                current_player = 0;
            }

            // Check win.
            for (int[] i : winning_positions)
            {
                if (game_state[i[0]] == game_state[i[1]] && game_state[i[1]] == game_state[i[2]])
                {
                    if (game_state[i[0]] != 2)
                    {
                        if (game_state[i[0]] == 0)
                        {
                            winner = "Player X";
                        }
                        else
                        {
                            winner = "Player O";
                        }
                        Toast.makeText(this, "Winner: " + winner, Toast.LENGTH_SHORT).show();
                        game_over = true;
                    }
                }
            }

            // Check tie.
            boolean tie = true;
            if (!game_over)
            {
                for (int i : game_state)
                {
                    if (i == 2)
                    {
                        tie = false;
                        break;
                    }
                }

                if (tie)
                {
                    Toast.makeText(this, "Tie!", Toast.LENGTH_SHORT).show();
                    game_over = true;
                }
            }
        }

        if (game_over)
        {
            // Make gameover layout visible.
            LinearLayout gameover = (LinearLayout) findViewById(R.id.gameover_layout);

            TextView announcement = (TextView) findViewById(R.id.text_annouce);
            if (winner != "")
            {
                announcement.setText("Winner is " + winner + "!");
            }
            else
            {
                announcement.setText("Tied!");
            }

            // Make board transparent.
            GridLayout board = (GridLayout) findViewById(R.id.board_grid);
            board.setAlpha(0.5f);

            gameover.setVisibility(View.VISIBLE);
        }
    }

    public void play_again(View view)
    {
        game_over = false;
        winner = "";

        LinearLayout gameover = (LinearLayout) findViewById(R.id.gameover_layout);
        gameover.setVisibility(View.INVISIBLE);

        GridLayout board = (GridLayout) findViewById(R.id.board_grid);
        board.setAlpha(1f);

        current_player = 0;

        for (int i = 0; i < game_state.length; ++i)
        {
            game_state[i] = 2;
        }

        for (int i = 0; i < board.getChildCount(); ++i)
        {
            ((ImageView) board.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
