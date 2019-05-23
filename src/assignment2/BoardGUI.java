package assignment2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
//import javafx.scene.paint.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Arash
 */
//to do:
//showing whose turn it is
public class BoardGUI {
    
    private JButton[][] buttons;
    private Board board;
    private JPanel boardPanel;
    private JLabel turnLabel;
    private ArrayList<Point> points;
    
    private Random random = new Random();
    private ArrayList<Player> players;
    private Player clicker;
    private boolean gameEnded;
    private int cntOfUnsignedFields;
    
//    private final int NUM_ADJACENT_SIGNS = 5;
    
    //constructor
    public BoardGUI(int boardSize) {
        board = new Board(boardSize);
        boardPanel = new JPanel();
        points = new ArrayList<>();
        gameEnded = false;
        cntOfUnsignedFields = boardSize*boardSize;
        
        players = new ArrayList<>();
        Player player1 = new Player();
        player1.setSign('X');
        Player player2 = new Player();
        player2.setSign('O');
        players.add(player1);
        players.add(player2);
        
        players.get(0).setName(JOptionPane.showInputDialog(null, "First player's name: "));
        players.get(1).setName(JOptionPane.showInputDialog(null, "Second player's name: "));
        clicker = player1;
//        Collections.shuffle(players);
        
        boardPanel.setLayout(new GridLayout(board.getBoardSize(), board.getBoardSize()));
        buttons = new JButton[board.getBoardSize()][board.getBoardSize()];
        for (int i = 0; i < board.getBoardSize(); ++i) {
            for (int j = 0; j < board.getBoardSize(); ++j) {
                JButton button = new JButton();
                button.addActionListener(new ButtonListener(i, j));
                button.setPreferredSize(new Dimension(80, 40));
                button.setForeground(java.awt.Color.WHITE);
                button.setBackground(java.awt.Color.BLACK);
                button.setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j] = button;
                boardPanel.add(button);
                points.add(new Point(i, j));
            }
        }

        
        turnLabel = new JLabel(clicker.getName() + "'s turn");
        turnLabel.setHorizontalAlignment(JLabel.RIGHT);
        
//        Collections.shuffle(points);
    //time stuff here vvv (if needed)
    }
    
    //use this after modification of a field to show the changes
    public void refresh(int x, int y) {
        JButton button = buttons[x][y];
        Field field = board.getField(x, y);
        button.setText(Character.toString(field.getSign()));
    }
    
    
    public JPanel getBoardPanel() {
        return boardPanel;
    }
    
    //how to add this to the UML diagram?
    //can we define it in a separate file?
    class ButtonListener implements ActionListener {
        private int x, y;
        private ArrayList<Field> adjSignedFields;
        
        //constructor
        public ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
            this.adjSignedFields = new ArrayList<>();
        }
        
        //based on the perform action does something
        @Override
        public void actionPerformed(ActionEvent e) {
            char signOfButton = board.getField(x, y).getSign();
            
            if (signOfButton != 'O' && signOfButton != 'X') {
                board.getField(x, y).setSign(clicker.getSign());
                refresh(x, y);
                cntOfUnsignedFields--;
                signOfButton = board.getField(x, y).getSign();
                clicker.addToSignedFields(x, y);
                
                //**
                verticalSignCheck();
                if (!gameEnded)
                    HorizontalSignCheck();
                if (!gameEnded)
                    firstDiagonalSignCheck();
                if (!gameEnded)
                    secondDiagonalSignCheck();
                //**
                if (!gameEnded)
                    nextPlayer();
                if(cntOfUnsignedFields == 0)
                    endGame();
            }
        }
        
        //checks for the fields with the same sign which are adjacent vertically
        public void verticalSignCheck() {
            char signOfButton = board.getField(x, y).getSign();
            
            adjSignedFields = new ArrayList<>();
            adjSignedFields.add(board.getField(x, y));
            
            for (int i = x+1; i < x+5; ++i) {
                if (i == board.getBoardSize()) { //out of range
                    break;
                }
                if (signOfButton != board.getField(i, y).getSign()) {
                    break;
                } else {
                    adjSignedFields.add(board.getField(i, y));
                }
            }
            
            for (int i = x-1; i > x-5; --i) {
                if ( i == -1) { //out of range
                    break;
                }
                if (signOfButton != board.getField(i, y).getSign()) {
                    break;
                } else {
                    adjSignedFields.add(board.getField(i, y));
                } 
            }
            
            if (adjSignedFields.size() == 3) {
                randomUnsign(1, signOfButton);
            } else if (adjSignedFields.size() == 4) {
                randomUnsign(2, signOfButton);
            } else if (adjSignedFields.size() >= 5) {
                endGame(clicker);
            }
        }
        
        //checks for the fields with the same sign which are adjacent Horizontally
        public void HorizontalSignCheck() {
            char signOfButton = board.getField(x, y).getSign();
            
            adjSignedFields = new ArrayList<>();
            adjSignedFields.add(board.getField(x, y));
            
            for (int j = y+1; j < y+5; ++j) {
                if (j == board.getBoardSize()) { //out of range 
                    break;
                }
                if (signOfButton != board.getField(x, j).getSign()) {
                    break;
                } else {
                    adjSignedFields.add(board.getField(x, j));
                }
            }
            for (int j = y-1; j > y-5; --j) {
                if (j == -1) { //out of range
                    break;
                }
                if (signOfButton != board.getField(x, j).getSign()) {
                    break;
                } else {
                    adjSignedFields.add(board.getField(x, j));
                }
            }
            
            if (adjSignedFields.size() == 3) {
                randomUnsign(1, signOfButton);
            } else if (adjSignedFields.size() == 4) {
                randomUnsign(2, signOfButton);
            } else if (adjSignedFields.size() >= 5) {
                endGame(clicker);
            }
        }
        
        //checks for the fields with the same sign which are adjacent on the 135 degree diagonal
        public void firstDiagonalSignCheck() {
            char signOfButton = board.getField(x, y).getSign();
            
            adjSignedFields = new ArrayList<>();
            adjSignedFields.add(board.getField(x, y));
            
            int i = x+1;
            int j = y+1;
            while (i < x+5 && j < y+5 && i != board.getBoardSize() && j != board.getBoardSize()) {
                
                if (signOfButton != board.getField(i, j).getSign()) {
                    break;
                } else {
                    adjSignedFields.add(board.getField(i, j));
                }
                i++;
                j++;
            }
            
            i = x-1;
            j = y-1;
            while (i > x-5 && j > y-5 && i != -1 && j != -1) {
                if (signOfButton != board.getField(i, j).getSign()) {
                    break;
                } else {
                    adjSignedFields.add(board.getField(i, j));
                }
                i--;
                j--;
            }
            
            if (adjSignedFields.size() == 3) {
                randomUnsign(1, signOfButton);
            } else if (adjSignedFields.size() == 4) {
                randomUnsign(2, signOfButton);
            } else if (adjSignedFields.size() >= 5) {
                endGame(clicker);
            }
        }
        
        //checks for the fields with the same sign which are adjacent on the 45 degree diagonal
        public void secondDiagonalSignCheck() {
            char signOfButton = board.getField(x, y).getSign();
            
            adjSignedFields = new ArrayList<>();
            adjSignedFields.add(board.getField(x, y));
            
            int i = x+1;
            int j = y-1;
            while (i < x+5 && j > y-5 && i != board.getBoardSize() && j != -1) {
                
                if (signOfButton != board.getField(i, j).getSign()) {
                    break;
                } else {
                    adjSignedFields.add(board.getField(i, j));
                }
                i++;
                j--;
            }
            
            i = x-1;
            j = y+1;
            while (i > x-5 && j < y+5 && i != -1 && j != board.getBoardSize()) {
                if (signOfButton != board.getField(i, j).getSign()) {
                    break;
                } else {
                    adjSignedFields.add(board.getField(i, j));
                }
                i--;
                j++;
            }
            
            if (adjSignedFields.size() == 3) {
                randomUnsign(1, signOfButton);
            } else if (adjSignedFields.size() == 4) {
                randomUnsign(2, signOfButton);
            } else if (adjSignedFields.size() >= 5) {
                endGame(clicker);
            }
        }
        
        //changes the turn of the players
        public void nextPlayer() {
            if (clicker == players.get(0)) {
                clicker = players.get(1);
            } else if (clicker == players.get(1)) {
                clicker = players.get(0);
            } else {
                System.out.println("Error in associating clicker and a player.");
            }
            turnLabel.setText(clicker.getName() + "'s turn");
        }
        
        //unsigns randomly "number" number of signed fields with the same sign of "signOfButton"
        //We make sure not to unsign the button which just got pushed.
        public void randomUnsign(int number,char signOfButton) {
            if (clicker.getSignedFields().size() >= number + 2) {
                Point p = new Point();
                int index;

                for (int i = 0; i < number; ++i) {

                    do {
                        index = random.nextInt(clicker.getSignedFields().size()-1);
                        p = clicker.getSignedFields().get(index);
                    } while (this.x == p.x && this.y == p.y);

                    board.getField(p.x, p.y).setSign(' ');
                    clicker.removeFromSignedFields(p.x, p.y);
                    refresh(p.x, p.y);
                    cntOfUnsignedFields++;
                }
            }
        }
        
        public void setSignOfButton(char sign) {
        }
        
    }
    
    
    //ends the game and anounces the winner.
    public void endGame(Player player){
        player.setWon(true);
        this.gameEnded = true;
        JOptionPane.showMessageDialog(boardPanel, player.getName() + " Won",
            "Congrats!", JOptionPane.PLAIN_MESSAGE);
    }
    
    //ends the game and announces Draw.
    public void endGame() {
        this.gameEnded = true;
        JOptionPane.showMessageDialog(boardPanel, "Draw",
            "Nice Game!", JOptionPane.PLAIN_MESSAGE);
    }
    
    
    public JLabel getTurnLabel() {
        return turnLabel;
    }

    
}


