package com.example.simondice2;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart;
    private ArrayList<Integer> colorSequence;
    private int playerIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.buttonStart);
        colorSequence = new ArrayList<>();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }

    private void startGame() {
        colorSequence.clear();
        score = 0;
        playerIndex = 0;
        addRandomColor();
    }

    private void addRandomColor() {
        int randomColor = new Random().nextInt(4);
        colorSequence.add(randomColor);
        showColorSequence();
    }

    private void showColorSequence() {
        buttonStart.setEnabled(false);
        final int delay = 1000;
        final int[] currentIndex = {0};
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentIndex[0] < colorSequence.size()) {
                    highlightButton(colorSequence.get(currentIndex[0]));
                    currentIndex[0]++;
                    handler.postDelayed(this, delay);
                } else {
                    playerIndex = 0;
                    buttonStart.setEnabled(true);
                }
            }
        }, delay);
    }

    private void highlightButton(int colorIndex) {
        Button[] buttons = {
                findViewById(R.id.buttonRojo),
                findViewById(R.id.buttonAzul),
                findViewById(R.id.buttonVerde),
                findViewById(R.id.buttonAmarillo)
        };

        Button button = buttons[colorIndex];
        button.setBackgroundColor(Color.WHITE);

        final int delay = 500;
        button.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setBackgroundColor(Color.DKGRAY);
                playerTurn();
            }
        }, delay);
    }

    private void playerTurn() {

        Button[] buttons = {
                findViewById(R.id.buttonRojo),
                findViewById(R.id.buttonAzul),
                findViewById(R.id.buttonVerde),
                findViewById(R.id.buttonAmarillo)
        };

        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (index == colorSequence.get(playerIndex)) {
                        playerIndex++;
                        if (playerIndex == colorSequence.size()) {
                            // Player matched the entire sequence.
                            score++;
                            addRandomColor();
                        }
                    } else {

                        gameOver();
                    }
                }
            });
        }
    }

    private void gameOver() {
        buttonStart.setEnabled(true);
}
}