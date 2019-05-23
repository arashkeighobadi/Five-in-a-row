package assignment2;

import java.awt.Point;

/**
 *
 * @author Arash
 */
public class Board {
    private Field[][] board;
    private final int boardSize;
    
    //constructor
    public Board(int boardSize) {
        this.boardSize = boardSize;
        board = new Field[this.boardSize][this.boardSize];
        for (int i = 0; i < this.boardSize; ++i) {
            for (int j = 0; j < this.boardSize; ++j) {
                board[i][j] = new Field();
            }
        }
    }
    
//    public boolean isOver() {
//       //if there's no more empty field to click -> draw
//       //if one player has 5 adjacent signs vertically, horizontally or diagonally
//       return false; // edit
//    }
    
    public Field getField(int x, int y) {
        return board[x][y];
    }
    
    public Field getField(Point point) {
        int x = (int)point.getX();
        int y = (int)point.getY();
        return getField(x, y);
    }
    
    public int getBoardSize() {
        return boardSize;
    }
    
}
