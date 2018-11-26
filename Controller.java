package setGame;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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



public class Controller extends TimerTask implements MouseListener, KeyListener
{
	private JFrame gameJFrame;
    private Container gameContentPane;
    private boolean playerTurn = false;
    private java.util.Timer gameTimer = new java.util.Timer();
    public Image[] imageArray = new Image[81];
    public int[] xPosition = {350, 550, 750};
    public int[] yPosition = {20, 140, 260, 380, 500, 620, 740};
    public int currentCard = 0;
    public Card[] selectedCards = new Card[3];//Array of selected cards for isThisASet method
    public int cardsOnScreen = 0; 
    public DeckOfCards deck = new DeckOfCards();
    Card[] allCards = new Card[81];//Array of entire deck
    public int[] scores = {0, 0, 0, 0};
    public String[] playerNames = new String[4];
    public String[] playerNums = {"W","O","X","N"};
    public int numberPlayers = 0;
	public JLabel[] playerLabel = new JLabel[4];
	Font myFont = new Font("Serif", Font.BOLD, 30);
	public int whosTurn;
	JLabel[] labels = new JLabel[81];
	boolean cardSpot[] = new boolean[21];
	int cardsOnTable[] = new int[21];//Array of ints refers back to card position in allCards array
    int col;//columns 0-3 
    int row;// rows 0-6

    
	 public Controller(String passedInWindowTitle, int gameWindowX, int gameWindowY, int gameWindowWidth, int gameWindowHeight) throws IOException
	 {
		 for(int i = 0; i < 81; i++)
		 {
			 allCards[i] = deck.getCard(i);
		 }
		 DeckOfCards.Shuffle(allCards);
		 for(int i = 0; i < 81; i++)
		 {
			 Image myPicture = allCards[i].getPicture();
			 imageArray[i] = myPicture;
		 }
        gameJFrame = new JFrame(passedInWindowTitle);
        gameJFrame.setSize(gameWindowWidth, gameWindowHeight);
        gameJFrame.setLocation(gameWindowX, gameWindowY);
        gameJFrame.setResizable(false);
        gameJFrame.addMouseListener(this);
        gameJFrame.addKeyListener(this);
        gameJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameContentPane = gameJFrame.getContentPane();
        gameContentPane.setLayout(null);
        gameContentPane.setBackground(Color.white);
       
        numberPlayers=Integer.parseInt(JOptionPane.showInputDialog("Enter Number of Players(1-4): "));
        int i=0;
        while(i<numberPlayers){
           try{
               playerNames[i]=(JOptionPane.showInputDialog("Enter Name for Player: " + (i+1)+ "("+playerNums[i]+")"));
               i++;
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Please enter valid number");
            }
        }
       drawCards(allCards, 15);
       drawScore(numberPlayers, playerNames, scores);
       gameJFrame.setVisible(true);
	 }
	 
	public void drawScore(int numberPlayers, String[] playerNames, int[] scores)
	{
		JLabel label = new JLabel();
		label.setBounds(0, 0, 333, 1000);
		label.setBackground(Color.GRAY);
		label.setOpaque(true);
		gameJFrame.add(label);
		for(int i = 0; i < numberPlayers; i++)
		{
			playerLabel[i] = new JLabel("<html>"+playerNames[i]+"("+ playerNums[i]+")"+": " +"<br>"+scores[i]+"</html>");
			playerLabel[i].setBounds(50, (100 +(i*100)), 200, 100);
			playerLabel[i].setFont(myFont);
			playerLabel[i].setBackground(Color.GRAY);
			playerLabel[i].setOpaque(true);
			label.add(playerLabel[i]);
			gameJFrame.repaint();
		}
	}

	public void drawCards(Card[] images, int numberOfCards)
	 {
		 for(int i = 0; i < labels.length; i++)
		 { 
			 Image temp = images[i].getPicture();
			 Image newImage = temp.getScaledInstance(145, 81, Image.SCALE_DEFAULT);
			 JLabel label = new JLabel(new ImageIcon(newImage)); 
			 labels[i] = label;
		 }
		 int i =0;
		 for(int y = 0; y < yPosition.length; y++)
		 {
			 for(int x = 0; x < xPosition.length; x++)
			 {
				 if(!cardSpot[(y*3)+x])
				 {
					 if(i == numberOfCards)
					 {
						 break;
					 }
					 labels[currentCard].setBounds(xPosition[x], yPosition[y], 145, 81);
					 gameJFrame.add(labels[currentCard]);
					 cardsOnTable[(y*3)+x] = currentCard;
					 currentCard++;
					 cardsOnScreen++;
					 cardSpot[(y*3)+x] = true;
					 i++;
				 }
			 }
		 }
		gameJFrame.repaint();
	 }
	public void removeCards(int pos) 
	{
		gameJFrame.remove(labels[pos]);
		gameJFrame.repaint();
	}
	public void keyPressed(KeyEvent e) 
	{
		if(!playerTurn)
		{
		if(e.getKeyCode() == KeyEvent.VK_W)
		{
			playerTurn = true;
			whosTurn = 0;
			System.out.println("W");
		}
		if(e.getKeyCode() == KeyEvent.VK_O)
		{
			playerTurn = true;
			whosTurn = 1;
			System.out.println("O");
		}
		if(e.getKeyCode() == KeyEvent.VK_X)
		{
			playerTurn = true;
			whosTurn = 2;
			System.out.println("X");
		}
		if(e.getKeyCode() == KeyEvent.VK_N)
		{
			playerTurn = true;
			whosTurn = 3;
			System.out.println("N");
		}
		}
	}
	int cardsSelected = 0;
	@Override
	public void mousePressed(MouseEvent e) 
		{
			if(playerTurn)
			{
				int[] cardPosArray = new int[3];
				boolean cardFound = false;
				int cardPos;
				int modNum = cardsOnScreen/3;
				int x=e.getX();
			    int y=e.getY();
			   		
			    if(x >= xPosition[0] && x <= xPosition[0]+ 145)
			    {	
			    	col = 0;

			    }
			    else if(x >= xPosition[1] && x <= xPosition[1]+ 145)
			    {
			    	col = 1;
			    }

			    else if(x >= xPosition[2] && x <= xPosition[2]+ 145)
			    {
			    	col = 2;
			    }
			    if(y >= yPosition[0]+20 && y <= yPosition[0]+ 101)
			    {	
			    	row = 0;
			    }
			    else if(y >= yPosition[1]+20 && y <= yPosition[1]+ 101)
			    {
			    	row = 1;
			    }

			    else if(y >= yPosition[2]+20 && y <= yPosition[2]+ 101)
			    {
			    	row = 2;
			    }
			    else if(y >= yPosition[3]+20 && y <= yPosition[3]+ 101)
			    {
			    	row = 3;
			    }

			    else if(y >= yPosition[4]+20 && y <= yPosition[4]+ 101)
			    {
			    	row = 4;
			    }
			    else if(y >= yPosition[5]+20 && y <= yPosition[5]+ 101)
			    {
			    	row = 5;
			    }

			    else if(y >= yPosition[6]+20 && y <= yPosition[6]+ 101)
			    {
			    	row = 6;
			    }
			    System.out.println((col) + (row*3));
			   System.out.println(allCards[cardsOnTable[col+(row*3)]].getFill()+ ","+ allCards[cardsOnTable[col+(row*3)]].getColor()+","+allCards[cardsOnTable[col+(row*3)]].getNumber()+","+allCards[cardsOnTable[col+(row*3)]].getShape());
			   System.out.println(cardsOnTable[col+(row*3)]);
			}
		}

	@Override
	

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
public static void main( String args[]) throws IOException
	{
	//		DeckOfCards deck = new DeckOfCards();
//		deck.displayDeck();
       Controller myController = new Controller("Does this work?", 450, 20, 1000, 1000);// window title, int gameWindowX, int gameWindowY, int gameWindowWidth, int gameWindowHeight){
        
	}
}
