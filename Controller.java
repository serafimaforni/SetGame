package setGame;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class Controller extends TimerTask implements MouseListener
{
	private JFrame gameJFrame;
    private Container gameContentPane;
    private boolean gameIsReady = false;
    private java.util.Timer gameTimer = new java.util.Timer();
    private int xMouseOffsetToContentPaneFromJFrame = 0;
    private int yMouseOffsetToContentPaneFromJFrame = 0;
   
    
	 public Controller(String passedInWindowTitle, int gameWindowX, int gameWindowY, int gameWindowWidth, int gameWindowHeight) throws IOException
	 {
		 DeckOfCards deck = new DeckOfCards();
		 deck.displayDeck();
	    BufferedImage myPicture = ImageIO.read(new File("setCards.png"));
	    Image[] imageArray = new Image[1];
	    imageArray[0] = myPicture;
	 
        gameJFrame = new JFrame(passedInWindowTitle);
        gameJFrame.setSize(gameWindowWidth, gameWindowHeight);
        gameJFrame.setLocation(gameWindowX, gameWindowY);
        gameJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameContentPane = gameJFrame.getContentPane();
        gameContentPane.setLayout(new GridLayout());
        gameContentPane.setBackground(Color.white);
        int borderWidth = (gameWindowWidth - gameContentPane.getWidth())/2;  // 2 since border on either side
        xMouseOffsetToContentPaneFromJFrame = borderWidth;
        yMouseOffsetToContentPaneFromJFrame = gameWindowHeight - gameContentPane.getHeight()-borderWidth; // assume side border = bottom border; ignore title bar
         JLabel test = new JLabel(new ImageIcon(imageArray[0]));
      
        int a=JOptionPane.showConfirmDialog(gameJFrame,"Would you like to start the game?");  
        if(a==JOptionPane.YES_OPTION){  
            gameJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            gameJFrame.add(test);
        gameJFrame.setVisible(true);
        }
	 }

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	public static void main( String args[]) throws IOException{
       Controller myController = new Controller("Does this work?", 450, 20, 1000, 1000);// window title, int gameWindowX, int gameWindowY, int gameWindowWidth, int gameWindowHeight){
         
	}

}
