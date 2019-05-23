package assignment2;




/**
 *
 * @author Arash
 */

import java.awt.Color;

public class Field {
    private char sign;
    
    //constructor
    public Field() {
        this.sign = Character.MIN_VALUE;
    }
    
    
    public char getSign() {
        return sign;
    }
    
    
    public void setSign(char sign) {
        this.sign = sign;
    }
    
}
