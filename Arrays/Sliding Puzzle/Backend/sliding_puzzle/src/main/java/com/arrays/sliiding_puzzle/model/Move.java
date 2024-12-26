package com.arrays.sliiding_puzzle.model;

public class Move {
    private Position firstClick;
    private Position secondClick;
    private PuzzleModel puzzleModel;


    public PuzzleModel getModel(){
        return puzzleModel;
    }
    public int[][] getPuzzleModel(){
        return puzzleModel.getPuzzleBoard();
    }


    public Position getFirstClick() {
        return firstClick;
    }

    public void setFirstClick(Position firstClick) {
        this.firstClick = firstClick;
    }

    public Position getSecondClick() {
        return secondClick;
    }

    public void setSecondClick(Position secondClick) {
        this.secondClick = secondClick;
    }
}
