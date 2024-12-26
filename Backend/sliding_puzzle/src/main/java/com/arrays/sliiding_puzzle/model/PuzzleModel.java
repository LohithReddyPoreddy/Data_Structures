package com.arrays.sliiding_puzzle.model;


import lombok.Data;

@Data
public class PuzzleModel {
    private int[][] puzzleBoard;
    private int size;
    private boolean completed;
    private int moves;


    public PuzzleModel(int[][] puzzleBoard) {
        this.puzzleBoard = puzzleBoard;
        this.size = puzzleBoard.length;
        this.completed = false;
        this.moves = 0;
    }

    public boolean isSolved(){
        int counter = 1;
        for(int i = 0 ;i < size; i++){
            for(int j = 0 ; j < size ; j++){
                int expected = (i==size-1 && j==size-1)?0:counter;
                if(puzzleBoard[i][j] != expected){
                    return false;
                }
                counter++;
            }
        }
        return true;
    }

    public int[][] getPuzzleBoard() {
        return puzzleBoard;
    }

    public void setPuzzleBoard(int[][] puzzleBoard) {
        this.puzzleBoard = puzzleBoard;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }
    public void incrementMoves() {
        this.moves++;
    }
}
