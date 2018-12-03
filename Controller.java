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
import java.security.SecureRandom;
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
    private static SecureRandom randomGenerator = new SecureRandom();
    private java.util.Timer gameTimer = new java.util.Timer();
    public Image[] imageArray = new Image[81];
    public int[] xPosition = {350, 550, 750};
    public int[] yPosition = {20, 140, 260, 380, 500, 620, 740};
    public int currentCard = 0;
    public Card[] selectedCards = new Card[3];//Array of selected cards for isThisASet method
    public DeckOfCards deck = new DeckOfCards();
    Card[] allCards = new Card[81];//Array of entire deck
    public int[] scores = {0, 0, 0, 0};
    public String[] playerNames = new String[4];
    public String[] playerNums = {"W","O","X","N"};
    public int numberPlayers = 0;
	public JLabel[] playerLabel = new JLabel[4];
	Font myFont = new Font("Serif", Font.BOLD, 30);
	public int whosTurn;//used for player label array to find who's turn it is
	JLabel[] labels = new JLabel[81];//JLabels of cards to print to screen
	boolean cardSpot[] = new boolean[21];//Where each card is on the screen
	int cardsOnTable[] = new int[21];//Array of ints refers back to card position in allCards array
    int col;//columns 0-3 
    int row;// rows 0-6
	int[] cardPosArray = new int[3];//For isThisASet method to store the array position to remove cards
	int cardsOnScreen = 0;
	int scoreTimer = 30;
	int playerTimer = 0;
	JLabel label = new JLabel();
	JLabel scoreTimerLabel = new JLabel();

    
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

		 for(int i = 0; i < labels.length; i++)
		 { 
			 Image temp = allCards[i].getPicture();
			 Image newImage = temp.getScaledInstance(145, 81, Image.SCALE_DEFAULT);
			 JLabel label = new JLabel(new ImageIcon(newImage));
			 labels[i] = label;
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
       drawCards(12);
       drawScore(numberPlayers, playerNames, scores);
       gameJFrame.setVisible(true);
       gameTimer.schedule(this, 0, 1000);
       if(currentCard == 81 && !isThereASet(cardsOnScreen))
       {
    	   drawgameOver();
       }
	 }
	 
	private void drawgameOver() 
	{
		
	}

	public void drawScore(int numberPlayers, String[] playerNames, int[] scores)
	{
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
		}
			gameJFrame.repaint();
	       
	}

	public void drawCards(int numberOfCards)
	 {
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
					 cardSpot[(y*3)+x] = true;
					 System.out.println("Card Spot: "+currentCard+", "+cardSpot[(y*3)+x]+", "+cardsOnTable[(y*3)+x]);
					 currentCard++;
					 cardsOnScreen++;
					 i++;
				 }
			 }
		 }
		gameJFrame.repaint();
	 }
	public void removeCards(int pos) 
	{
		gameJFrame.remove(labels[cardsOnTable[pos]]);
		cardSpot[pos] = false;
		cardsOnScreen--;
		System.out.println("Card Spot: "+pos+", "+cardsOnTable[pos]+", "+cardSpot[pos]);
		gameJFrame.repaint();
	}
	public void makeBig(int pos)
	{
		 labels[pos].setBounds(labels[pos].getX(), labels[pos].getY()-20, 145, 81);;
		gameJFrame.repaint();
	}
	public void makeSmall(int pos)
	{
		 labels[pos].setBounds(labels[pos].getX(), labels[pos].getY()+20, 145, 81);;
		gameJFrame.repaint();
	}
	public boolean isThereASet(int numCardsOnScreen)
	{
		boolean setExists = false;
		if(numCardsOnScreen == 21)
		{
			setExists = true;
		}
		else if(numCardsOnScreen < 21 && numCardsOnScreen > 0)
		{
			for(int i = 0; i < numCardsOnScreen-1; i++)
			{
				for(int j = 0; j < numCardsOnScreen; j++)
				{
					if(DeckOfCards.isThisASet( allCards[cardsOnTable[i]],  allCards[cardsOnTable[i+1]],  allCards[cardsOnTable[j]]))
					{
						setExists = true;
					}
				}
		
			}
		}
			
		return setExists;
		
	}
//	public void randomize()
//	{
//		for(int first = 0; first < cardsOnScreen; first++)
//		{
//			int dest = randomGenerator.nextInt(cardsOnScreen-1);
//			int tempPos = cardPosArray[first];
//			JLabel tempLabel = labels[first];
//			cardPosArray[first] = cardPosArray[dest];
//			labels[first] = labels[dest];
//			cardPosArray[dest] = tempPos;
//			labels[dest] = tempLabel;
//			gameJFrame.repaint();
//		}
//		
//	}
	public void keyPressed(KeyEvent e) 
	{
		if(!playerTurn)
		{
			if(e.getKeyCode() == KeyEvent.VK_W)
			{
				playerTurn = true;
				whosTurn = 0;
				playerLabel[0].setBackground(Color.GREEN);
				System.out.println("W");
			}
			if(e.getKeyCode() == KeyEvent.VK_O)
			{
				playerTurn = true;
				whosTurn = 1;
				playerLabel[1].setBackground(Color.RED);
				System.out.println("O");
			}
			if(e.getKeyCode() == KeyEvent.VK_X)
			{
				playerTurn = true;
				whosTurn = 2;
				playerLabel[2].setBackground(Color.BLUE);
				System.out.println("X");
			}
			if(e.getKeyCode() == KeyEvent.VK_N)
			{
				playerTurn = true;
				whosTurn = 3;
				playerLabel[3].setBackground(Color.YELLOW);
				System.out.println("N");
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				if(currentCard != 81)
				{
					scoreTimer = 30;
					drawCards(3);
				}
				else 
				{
					drawEndMessage();
				}
			}
		}
	}
	public void drawEndMessage()
	{
		JLabel endMessage = new JLabel();
		endMessage.setBounds(xPosition[0], yPosition[6]+60, 500, 60);
		endMessage.setFont(myFont);
		endMessage.setText("NO MORE CARDS LEFT IN DECK!");
		endMessage.setBackground(Color.black);
		gameJFrame.add(endMessage);
		gameJFrame.repaint();
	}
	int cardsSelected = 0;
	@Override
	public void mousePressed(MouseEvent e) 
		{
		boolean sameCard = false;
			if(playerTurn)
			{
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
				int cardPos = (col) + (row*3);
				if(cardSpot[cardPos])
				{
				   System.out.println((col) + (row*3));
				   System.out.println(allCards[cardsOnTable[cardPos]].getFill()+ ","+ allCards[cardsOnTable[cardPos]].getColor()+","+allCards[cardsOnTable[cardPos]].getNumber()+","+allCards[cardsOnTable[cardPos]].getShape());
				   System.out.println(cardsOnTable[cardPos]);
				   System.out.println(cardSpot[cardPos]);
				   makeBig(cardsOnTable[cardPos]);
				   selectedCards[cardsSelected] = allCards[cardsOnTable[cardPos]];
				   cardPosArray[cardsSelected] = cardPos;
				   cardsSelected++;
				   if(cardsSelected == 2)
				   {
					   if(cardPosArray[0]==cardPosArray[1])
					   {
						   sameCard = true;
						   cardsSelected--;
						   cardsSelected--;
						   makeSmall(cardsOnTable[cardPosArray[0]]);
						   makeSmall(cardsOnTable[cardPosArray[0]]);
					   }
				   }
				   if(cardsSelected == 3)
				   {
					   playerTimer = 0;
					   if(cardPosArray[1]==cardPosArray[2])
					   {
						   sameCard = true;
						   cardsSelected--;
						   cardsSelected--;
						   makeSmall(cardsOnTable[cardPosArray[1]]);
						   makeSmall(cardsOnTable[cardPosArray[1]]);
					   }
					   if(!sameCard)
					   {
					   playerLabel[whosTurn].setBackground(Color.GRAY);
					   playerTurn = false;
					   cardsSelected = 0;
					   if(DeckOfCards.isThisASet(selectedCards[0], selectedCards[1], selectedCards[2]))
					   {
						   removeCards(cardPosArray[0]);
						   removeCards(cardPosArray[1]);
						   removeCards(cardPosArray[2]);
						   scores[whosTurn]=scores[whosTurn]+scoreTimer;
						   playerLabel[whosTurn].setText("<html>"+playerNames[whosTurn]+"("+ playerNums[whosTurn]+")"+": " +"<br>"+scores[whosTurn]+"</html>");
					   }
					   makeSmall(cardsOnTable[cardPosArray[0]]);
					   makeSmall(cardsOnTable[cardPosArray[1]]);
					   makeSmall(cardsOnTable[cardPosArray[2]]);
					   }
					   scoreTimer = 30;
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
		System.out.println(scoreTimer);
		System.out.println(isThereASet(cardsOnScreen));
		scoreTimerLabel.setText("<HTML>"+"Score Timer: "+"<BR>"+ scoreTimer+"</HTML>");
	       scoreTimerLabel.setBounds(20, 0, 180, 80);
	       scoreTimerLabel.setFont(myFont);
	       scoreTimerLabel.setBackground(Color.GRAY);
	       label.add(scoreTimerLabel);
	       gameJFrame.repaint();
	       if(!playerTurn)
	       {
				scoreTimer--;
				if(scoreTimer >= 15)
				{
					scoreTimerLabel.setForeground(Color.BLACK);
				}
				else if(scoreTimer < 15)
				{
					scoreTimerLabel.setForeground(Color.RED);
				}
	    	   if(scoreTimer == 0)
	    	   {
	    		   scoreTimer = 30;
	    	   }
	       }
	       else
	       {
	    	   playerTimer++;
	    	   if(playerTimer == 7)
	    	   {
	    		   makeSmall(cardPosArray[0]);
	    		   makeSmall(cardPosArray[1]);
	    		   makeSmall(cardPosArray[2]);
	    		   playerTurn = false;
	    		   playerLabel[whosTurn].setBackground(Color.GRAY);
	    		   scoreTimer = 30;
	    		   playerTimer = 0;
	    	   }

	       }
		
	}
	
public static void main( String args[]) throws IOException
	{
	//		DeckOfCards deck = new DeckOfCards();
//		deck.displayDeck();
       Controller myController = new Controller("Does this work?", 450, 20, 1000, 1000);// window title, int gameWindowX, int gameWindowY, int gameWindowWidth, int gameWindowHeight){
        
	}
}
