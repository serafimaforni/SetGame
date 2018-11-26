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



public class Controller2 extends TimerTask implements MouseListener, KeyListener
{
	private JFrame gameJFrame;
    private Container gameContentPane;
    private boolean playerTurn = false;
    private java.util.Timer gameTimer = new java.util.Timer();
    public Image[] imageArray = new Image[81];
    public int[] xPosition = {350, 550, 750};
    public int[] yPosition = {20, 140, 260, 380, 500, 620, 740};
    public int currentCard = 0;
    public Card[] selectedCards = new Card[3];
    public int cardsOnScreen = 0; 
    public DeckOfCards deck = new DeckOfCards();
    Card[] allCards = new Card[81];
    public int[] scores = {0, 0, 0, 0};
    public String[] playerNames = new String[4];
    public String[] playerNums = {"W","O","X","N"};
    public int numberPlayers = 0;
	public JLabel[] playerLabel = new JLabel[4];
	Font myFont = new Font("Serif", Font.BOLD, 30);
	public int whosTurn;
	JLabel[] labels = new JLabel[81];
	boolean cardSpot[] = new boolean[21];

    
	 public Controller2(String passedInWindowTitle, int gameWindowX, int gameWindowY, int gameWindowWidth, int gameWindowHeight) throws IOException
	 {
		 for(int i = 0; i < 81; i++)
		 {
			 allCards[i] = deck.getCard(i);
		 }
//		 DeckOfCards.Shuffle(allCards);
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
			    for(int i = 0; i < modNum; i++)
			    {
				    if(cardFound)
				    	{
				    		System.out.println("Card Found");
				    		break;
				    	}
				   for(int j = 0; j < modNum; j++)
				   {
					   if(cardFound)
				    	{
				    		System.out.println("Card Found");
				    		cardFound = false;
				    		break;
				    	}
					   if(y >= (yPosition[i%modNum]+20) && y <= (yPosition[i%modNum] + 101))
					    {
				    		if(x >= xPosition[j%3] && x <= (xPosition[j%3] + 145))
				    		{
				    			cardPos = ((i%modNum)*3)+ j%3;
				    			System.out.print(allCards[cardPos].getShape());
				    			System.out.print(allCards[cardPos].getFill());
				    			System.out.print(allCards[cardPos].getNumber());
				    			System.out.print(allCards[cardPos].getColor());
				    			selectedCards[cardsSelected] = allCards[cardPos];
				    			System.out.print("---------------------");
//				    			DeckOfCards.isThisASet(selectedCards[0], selectedCards[1], selectedCards[2]);
//				    			System.out.println(cardsSelected);
				    			System.out.println(allCards[selectedCards[cardsSelected].getShape()+selectedCards[cardsSelected].getColor()+selectedCards[cardsSelected].getFill()+selectedCards[cardsSelected].getNumber()]);							
								cardPosArray[cardsSelected] = cardPos;
								cardsSelected++;
				    			cardFound = true;
				    			if(cardsSelected == 3)
				    			{
				    				System.out.println(DeckOfCards.isThisASet(selectedCards[0], selectedCards[1], selectedCards[2]));
				    				if(DeckOfCards.isThisASet(selectedCards[0], selectedCards[1], selectedCards[2]))
				    				{
				    					scores[whosTurn]++;
				    					playerLabel[whosTurn].setText("<html>"+playerNames[whosTurn]+"("+ playerNums[whosTurn]+")"+": " +"<br>"+scores[whosTurn]+"</html>");
				    					cardSpot[cardPosArray[0]] = false;
				    					cardSpot[cardPosArray[1]] = false;
				    					cardSpot[cardPosArray[2]] = false;
				    					removeCards(cardPosArray[0]);
				    					removeCards(cardPosArray[1]);
				    					removeCards(cardPosArray[2]);
				    					cardPosArray = null;
				    				}
				    				cardsSelected = 0;
				    				playerTurn = false;
				    				
				    			}
				    		}
				    	}
				    }
			    	
			    }
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
       Controller2 myController = new Controller2("Does this work?", 450, 20, 1000, 1000);// window title, int gameWindowX, int gameWindowY, int gameWindowWidth, int gameWindowHeight){
        
	}
}
