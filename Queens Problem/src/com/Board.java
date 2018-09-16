package com;

import java.util.Arrays;

public class Board {
    static final private int CELL_CODE_EMPTY = 0;
    static final private int CELL_CODE_QUEEN = 1;
    static final private int CELL_CODE_BLOCKED = -1;

    private int[][] board;
    private int size;
    private int recentlyInsertedRow = -1;
    private int recentlyInsertedCol = -1;
    private int queensCount = 0;

    Board(int n){
        board = new int[n][n];
        size = n;
        // java fills array with 0 (by default, but calling attachEveryNextState in case
        // we change cell code empty to be something other than 0
        for (int i = 0; i < n; i++) Arrays.fill(board[i], CELL_CODE_EMPTY);
    }

    Board getDuplicate(){
        Board ret = new Board(this.size);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                ret.board[i][j] = this.board[i][j];
        ret.recentlyInsertedCol = this.recentlyInsertedCol;
        ret.recentlyInsertedRow = this.recentlyInsertedRow;
        ret.queensCount = this.queensCount;
        return ret;
    }

    private void updateBoard(){
        for (int i = 0; i < size; i++) {
            // set recentlyInsertedRow of newest queen to be blocked if empty
            if (board[recentlyInsertedRow][i] == CELL_CODE_EMPTY)
                board[recentlyInsertedRow][i] = CELL_CODE_BLOCKED;
            // set column of newest queen to be blocked if empty
            if (board[i][recentlyInsertedCol] == CELL_CODE_EMPTY)
                board[i][recentlyInsertedCol] = CELL_CODE_BLOCKED;

            // do diagonal from piece to bottom right
            int row = (recentlyInsertedRow + i);
            int col = (recentlyInsertedCol + i);
            if (row < size && col < size && board[row][col] == CELL_CODE_EMPTY)
                board[row][col] = CELL_CODE_BLOCKED;

            // do diagonal from piece to top left
            row = (recentlyInsertedRow - i);
            col = (recentlyInsertedCol - i);
            if (row >= 0 && col >= 0 && board[row][col] == CELL_CODE_EMPTY)
                board[row][col] = CELL_CODE_BLOCKED;

            // do diagonal from piece to top right
            row = (recentlyInsertedRow - i);
            col = (recentlyInsertedCol + i);
            if (row >= 0 && col < size && board[row][col] == CELL_CODE_EMPTY)
                board[row][col] = CELL_CODE_BLOCKED;

            // do diagonal from piece to bottom left
            row = (recentlyInsertedRow + i);
            col = (recentlyInsertedCol - i);
            if (row < size && col >= 0 && board[row][col] == CELL_CODE_EMPTY)
                board[row][col] = CELL_CODE_BLOCKED;

        }

    }

    /**
     * Inserts queen at given coordinates, starting from 1 to 8, inclusively
     * @param row x coordinate, starting from top right
     * @param col y coordinate, starting from top right
     * @return true if queen was placed in specified location
     */
    public boolean insertQueen(int row, int col){
        row--;
        col--;
        // if given coordinates are out of bounds, return false
        if (row >= size || col >= size) return false;
        if (row < 0 || col < 0) return false;
        // if queen cant be placed in specified cell, return false
        if (board[row][col] != CELL_CODE_EMPTY) return false;

        recentlyInsertedRow = row;
        recentlyInsertedCol = col;
        board[row][col] = CELL_CODE_QUEEN;
        queensCount++;
        updateBoard();
        return true;
    }

    public String getFormattedBoard(){
        StringBuilder sb = new StringBuilder();
        for (int[] i : board){
            for (int n : i){
                String str;
                if (n == CELL_CODE_QUEEN) str = "Q ";
                //else if (n == CELL_CODE_BLOCKED) str = "X ";
                else str = "_ ";
                sb.append(str);
            }
            // append newline
            sb.append(String.format("%n"));
        }
        return sb.toString();
    }

    public int getSize() {
        return size;
    }

    public int getQueensCount() {
        return queensCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board board1 = (Board) o;
        return Arrays.equals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }
}
