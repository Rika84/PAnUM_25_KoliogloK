package com.example.lab_07.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lab_07.R;
import com.example.lab_07.data.AppDatabase;
import com.example.lab_07.data.GameStats;
import com.example.lab_07.utils.GameLogic;

import java.util.Date;
import java.util.List;

public class GameFragment extends Fragment {

    private GridLayout gridBoard;
    private TextView tvTurn;
    private Button btnRestart;

    private GameLogic gameLogic;
    private boolean gameOver = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        gridBoard = view.findViewById(R.id.gridBoard);
        tvTurn = view.findViewById(R.id.tvTurn);
        btnRestart = view.findViewById(R.id.btnRestart);

        gameLogic = new GameLogic();

        setupBoard();
        updateTurnText();

        btnRestart.setOnClickListener(v -> restartGame());

        return view;
    }

    private void setupBoard() {
        for (int i = 0; i < gridBoard.getChildCount(); i++) {
            int row = i / 3;
            int col = i % 3;

            View child = gridBoard.getChildAt(i);
            if (child instanceof Button) {
                Button btn = (Button) child;
                btn.setText("");
                btn.setEnabled(true);
                btn.setOnClickListener(v -> handleMove(btn, row, col));
            }
        }
    }

    private void handleMove(Button btn, int row, int col) {
        if (gameOver) return;

        char current = gameLogic.getCurrentPlayer();
        boolean validMove = gameLogic.makeMove(row, col);

        if (!validMove) return;

        btn.setText(String.valueOf(current));
        btn.setEnabled(false);

        if (gameLogic.checkWin()) {
            onWin(current);
        } else if (gameLogic.isDraw()) {
            onDraw();
        } else {
            gameLogic.switchPlayer();
            updateTurnText();
        }
    }

    private void onWin(char winner) {
        gameOver = true;
        Toast.makeText(getContext(), "Gracz " + winner + " wygrał!", Toast.LENGTH_SHORT).show();
        saveGameToDatabase(String.valueOf(winner));
        disableBoard();
        updateTurnText();
    }

    private void onDraw() {
        gameOver = true;
        Toast.makeText(getContext(), "Remis!", Toast.LENGTH_SHORT).show();
        saveGameToDatabase("Draw");
        disableBoard();
        updateTurnText();
    }

    private void disableBoard() {
        for (int i = 0; i < gridBoard.getChildCount(); i++) {
            View child = gridBoard.getChildAt(i);
            if (child instanceof Button) {
                child.setEnabled(false);
            }
        }
    }

    private void restartGame() {
        gameLogic.resetGame();
        gameOver = false;
        setupBoard();
        updateTurnText();
    }

    private void updateTurnText() {
        if (gameOver) {
            tvTurn.setText("Gra zakończona");
        } else {
            tvTurn.setText("Tura gracza: " + gameLogic.getCurrentPlayer());
        }
    }

    private void saveGameToDatabase(String winner) {
        if (getContext() == null) return;

        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            List<GameStats> allStats = db.gameStatsDao().getAll();

            int totalGames = 1;
            int winsX = 0;
            int winsO = 0;
            int draws = 0;

            if (!allStats.isEmpty()) {
                GameStats lastStats = allStats.get(0);
                totalGames = lastStats.totalGames + 1;
                winsX = lastStats.winsX;
                winsO = lastStats.winsO;
                draws = lastStats.draws;
            }

            switch (winner) {
                case "X":
                    winsX++;
                    break;
                case "O":
                    winsO++;
                    break;
                case "Draw":
                    draws++;
                    break;
            }

            GameStats newStats = new GameStats(new Date(), totalGames, winsX, winsO, draws);
            db.gameStatsDao().insert(newStats);
        });
    }
}