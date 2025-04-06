package com.example.lab_07.utils;

public class GameLogic {

    private final char[][] board = new char[3][3];
    private char currentPlayer;

    public GameLogic() {
        resetGame();
    }

    public void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '\0';
            }
        }
        currentPlayer = 'X';
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean makeMove(int row, int col) {
        if (board[row][col] == '\0') {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public boolean checkWin() {
        // wiersze i kolumny
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '\0' &&
                    board[i][0] == board[i][1] &&
                    board[i][1] == board[i][2]) return true;

            if (board[0][i] != '\0' &&
                    board[0][i] == board[1][i] &&
                    board[1][i] == board[2][i]) return true;
        }

        // przekątne
        if (board[0][0] != '\0' &&
                board[0][0] == board[1][1] &&
                board[1][1] == board[2][2]) return true;

        if (board[0][2] != '\0' &&
                board[0][2] == board[1][1] &&
                board[1][1] == board[2][0]) return true;

        return false;
    }

    public boolean isDraw() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '\0') return false;
            }
        }
        return !checkWin();
    }
}
