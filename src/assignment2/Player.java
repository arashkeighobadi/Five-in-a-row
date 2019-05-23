package assignment2;

import java.util.*;
import java.awt.Point;

/**
 *
 * @author Arash
 */
public class Player {
    private String name;
//    private int numOfAdjacentSigns;
    private boolean won;
    private char sign;
    private ArrayList<Point> signedFields;
    
    //constructor
    public Player(){
//        this.numOfAdjacentSigns = 0;
        this. won = false;
        signedFields = new ArrayList<>();
    }
    
    public void setWon(boolean won) {
        this.won = won;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setSign(char sign) {
        this.sign = sign;
    }
    
    public String getName() {
        return name;
    }
    
    public char getSign() {
        return sign;
    }
    
    public void addToSignedFields(int x, int y) {
        Point p = new Point(x, y);
        this.signedFields.add(p);
    }
    
    public void removeFromSignedFields(int i, int j) {
        Point p = new Point();
        boolean found = false;
        for (Point p1 : this.signedFields) {
            if (p1.x == i && p1.y == j) {
                p = p1;
                found = true;
                break;
            }
        }
        if (found)
            this.signedFields.remove(p);
    }
    
    public ArrayList<Point> getSignedFields() {
        return this.signedFields;
    }
    
}
