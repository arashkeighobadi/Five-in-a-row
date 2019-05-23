package assignment2;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Arash
 */
public class GameGUI {
    private JFrame frame;
    private BoardGUI boardGUI;
    
    private final int INITIAL_BOARD_SIZE = 6;
    
    
    //constructor
    public GameGUI() {
        frame = new JFrame("Tricky five-in-arow");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        boardGUI = new BoardGUI(INITIAL_BOARD_SIZE);
        frame.getContentPane().add(boardGUI.getBoardPanel(), BorderLayout.CENTER);
        frame.getContentPane().add(boardGUI.getTurnLabel(), BorderLayout.SOUTH);
        
        JMenuBar menuBar = new JMenuBar(); //why don't we make this a parameter of the class???
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        JMenu newMenu = new JMenu("New");
        gameMenu.add(newMenu);
        int[] boardSizes = new int[]{6, 10, 14};
        for (int boardSize : boardSizes) {
            JMenuItem sizeMenuItem = new JMenuItem(boardSize + "x" + boardSize);
            newMenu.add(sizeMenuItem);
            sizeMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(boardGUI.getBoardPanel());
                    frame.getContentPane().remove(boardGUI.getTurnLabel());
                    boardGUI = new BoardGUI(boardSize);
                    frame.getContentPane().add(boardGUI.getBoardPanel(),
                            BorderLayout.CENTER);
                    frame.getContentPane().add(boardGUI.getTurnLabel(), BorderLayout.SOUTH);
                    frame.pack();
                }
            });
        }
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) { //when is this name helpful? "ae"
                System.exit(0);
            }
        });
        
        frame.pack();
        frame.setVisible(true);
    }
}
