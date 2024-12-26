package com.arrays.sliiding_puzzle.service;

import com.arrays.sliiding_puzzle.model.Move;
import com.arrays.sliiding_puzzle.model.PuzzleModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;


import java.util.ArrayList;
import java.util.List;

@Service
public class PuzzleService {
    private Random random = new Random();
    private PuzzleModel currentPuzzle;



    public PuzzleModel createNewGame(int size){
        currentPuzzle = generatePuzzle(size);
        return currentPuzzle;
    }

    private PuzzleModel generatePuzzle(int size) {

        int[][] board = new int[size][size];
        int value = 1;
        for(int i = 0 ; i<size ; i++){
            for(int j = 0 ; j<size; j++){
                board[i][j] = value%(size*size);
                value++;
            }
        }
        shuffleBoard(board);
        return new PuzzleModel(board);
    }
    private void shuffleBoard(int[][] board) {
        int size = board.length;
        int moves = size * size * 100; // Number of random moves to shuffle

        // Start with empty tile at bottom-right
        int emptyRow = size - 1;
        int emptyCol = size - 1;

        // Possible move directions: up, right, down, left
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int lastMove = -1; // Track last move to avoid backtracking

        for (int i = 0; i < moves; i++) {
            // Array to store valid moves for current position
            int[] validMoves = new int[4];
            int validMoveCount = 0;

            // Check each direction
            for (int d = 0; d < 4; d++) {
                // Skip reverse of last move to avoid backtracking
                if (lastMove != -1 && (d + 2) % 4 == lastMove) {
                    continue;
                }

                int newRow = emptyRow + directions[d][0];
                int newCol = emptyCol + directions[d][1];

                if (isValidPosition(newRow, newCol, size)) {
                    validMoves[validMoveCount] = d;
                    validMoveCount++;
                }
            }

            if (validMoveCount > 0) {
                // Choose random valid move
                int moveIndex = random.nextInt(validMoveCount);
                int chosenDirection = validMoves[moveIndex];

                // Calculate new position
                int newRow = emptyRow + directions[chosenDirection][0];
                int newCol = emptyCol + directions[chosenDirection][1];

                // Perform the swap
                board[emptyRow][emptyCol] = board[newRow][newCol];
                board[newRow][newCol] = 0;

                // Update empty position and last move
                emptyRow = newRow;
                emptyCol = newCol;
                lastMove = chosenDirection;
            }
        }
    }

    private boolean isValidPosition(int row, int col, int size) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    public boolean isValidMove(Move move) {
        if (currentPuzzle == null) return false;


        int[][] board = currentPuzzle.getPuzzleBoard();

        int firstRow = move.getFirstClick().getRow();
        int firstCol = move.getFirstClick().getCol();
        int secondRow = move.getSecondClick().getRow();
        int secondCol = move.getSecondClick().getCol();

        System.out.println("First row" +firstRow + "First col :" + firstCol);
        System.out.println("Second row" +secondRow + "Second col :" + secondCol);

        // One piece must be empty (0)
        boolean isOneEmpty = board[firstRow][firstCol] == 0 ||
                board[secondRow][secondCol] == 0;

        return isOneEmpty && isAdjacent(firstRow, firstCol, secondRow, secondCol);
    }

    private boolean isAdjacent(int row1, int col1, int row2, int col2) {
        return (Math.abs(row1 - row2) == 1 && col1 == col2) ||
                (Math.abs(col1 - col2) == 1 && row1 == row2);
    }
    public PuzzleModel makeMove(Move move) {
        if (!isValidMove(move)) {
            return currentPuzzle;
        }

        int[][] board = currentPuzzle.getPuzzleBoard();
        int firstRow = move.getFirstClick().getRow();
        int firstCol = move.getFirstClick().getCol();
        int secondRow = move.getSecondClick().getRow();
        int secondCol = move.getSecondClick().getCol();

        swap(board, firstRow, firstCol, secondRow, secondCol);
        currentPuzzle.incrementMoves();

        if (currentPuzzle.isSolved()) {
            currentPuzzle.setCompleted(true);
        }

        return currentPuzzle;
    }

    private void swap(int[][] board, int row1, int col1, int row2, int col2) {
        int temp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = temp;
    }

    public PuzzleModel getCurrentPuzzle() {
        return currentPuzzle;
    }
}


